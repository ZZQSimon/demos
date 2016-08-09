package com.simon.framework.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

/**
 * @version 1.0
 * @parameter
 * @since
 * @date 2016-08-09 09:36:14 <br>
 * 
 * @author Simon
 */
public class HDFSUtils {

	private static Configuration conf = new Configuration();

	private static final Log log = LogFactory.getLog(HDFSUtils.class);

	private static String prefix = "hdfs://aclomecluster";

	private static final String[] PATHS = new String[] { "conf/hadoop/hbase-site.xml", "conf/hadoop/core-site.xml",
			"conf/hadoop/hdfs-site.xml" };

	static {
		Properties properties = new Properties();
		try {
			properties = PropertiesUtils.loadProperties("conf/hadoop/hdfs.properties");
		} catch (Exception e) {
			log.info("can't load hbase properties");
		}

		for (String path : PATHS) {
			conf.addResource(path);
		}

		if (properties.size() > 0) {
			Iterator<Object> iter = properties.keySet().iterator();
			String key = null;
			Object value = null;
			while (iter.hasNext()) {
				key = (String) iter.next();
				value = properties.get(key);
				if (value instanceof String) {
					conf.set(key, (String) value);
				}
				if (value instanceof Boolean) {
					conf.setBoolean(key, (Boolean) value);
				}
				if (value instanceof Long) {
					conf.setLong(key, (Long) value);
				}
				if (value instanceof Float) {
					conf.setFloat(key, (Float) value);
				}
				if (value instanceof Integer) {
					conf.setInt(key, (Integer) value);
				}
			}

			prefix = conf.get("fs.defaultFS");

		}
	}

	private static Path getPath(String path) {
		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		if (prefix.endsWith("/")) {
			if (!path.startsWith("/")) {
				sb.append(path);
			} else {
				sb.append(path.substring(1));
			}
		} else {
			if (!path.startsWith("/")) {
				sb.append("/").append(path);
			} else {
				sb.append(path);
			}
		}

		return new Path(sb.toString());
	}

	// create a direction
	public static void createDir(String dir) {
		FileSystem hdfs = null;
		try {
			hdfs = FileSystem.get(conf);
			hdfs.mkdirs(getPath(dir));
		} catch (Exception e) {
			log.error("HDFS 创建文件夹失败:" + e.getLocalizedMessage());
		} finally {
			if (hdfs != null) {
				try {
					hdfs.close();
				} catch (IOException e) {
					log.error("HDFS 关闭文件系统失败:" + e.getLocalizedMessage());
				}
			}
		}
	}

	public static void createNewHDFSFile(String toCreateFilePath, String content) {
		FileSystem hdfs = null;
		FSDataOutputStream os = null;
		try {
			hdfs = FileSystem.get(conf);
			os = hdfs.create(getPath(toCreateFilePath));
			os.write(content.getBytes("UTF-8"));

		} catch (Exception e) {
			log.error("HDFS 上传文件失败:" + e.getLocalizedMessage());
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					log.error("HDFS 关闭文件输出流失败:" + e.getLocalizedMessage());
				}
			}
			if (hdfs != null) {
				try {
					hdfs.close();
				} catch (IOException e) {
					log.error("HDFS 关闭文件系统失败:" + e.getLocalizedMessage());
				}
			}
		}
	}

	public static void uploadFile(String s, String d) {
		FileSystem hdfs = null;
		try {
			hdfs = FileSystem.get(conf);
			hdfs.copyFromLocalFile(new Path(s), getPath(d));
		} catch (Exception e) {
			log.error("HDFS 上传本地文件失败:" + e.getLocalizedMessage());
		} finally {
			if (hdfs != null) {
				try {
					hdfs.close();
				} catch (IOException e) {
					log.error("HDFS 关闭文件系统失败:" + e.getLocalizedMessage());
				}
			}
		}
	}

	public static boolean uploadFile(InputStream in, String path) {

		FileSystem hdfs = null;
		FSDataOutputStream out = null;
		try {
			hdfs = FileSystem.get(conf);
			out = hdfs.create(getPath(path), true);

			byte[] buf = new byte[1024];
			int readbytes = 0;
			while ((readbytes = in.read(buf)) > 0) {
				out.write(buf, 0, readbytes);
			}

			return true;
		} catch (Exception e) {
			log.error("HDFS 上传文件失败:" + e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error("HDFS 关闭文件输出流失败:" + e.getLocalizedMessage());
				}
			}
			if (hdfs != null) {
				try {
					hdfs.close();
				} catch (IOException e) {
					log.error("HDFS 关闭文件系统失败:" + e.getLocalizedMessage());
				}
			}
		}
		return false;
	}

	public static boolean deleteFile(String dst) throws IOException {
		FileSystem hdfs = null;
		try {
			hdfs = FileSystem.get(conf);
			return hdfs.delete(getPath(dst), true);
		} catch (Exception e) {
			log.error("HDFS 删除文件失败:" + e.getLocalizedMessage());
		} finally {
			if (hdfs != null) {
				try {
					hdfs.close();
				} catch (IOException e) {
					log.error("HDFS 关闭文件系统失败:" + e.getLocalizedMessage());
				}
			}
		}
		return false;
	}

	public static byte[] readHDFSFile(String dst) throws Exception {
		FileSystem hdfs = null;
		try {
			hdfs = FileSystem.get(conf);

			// check if the file exists
			Path path = getPath(dst);
			if (hdfs.exists(path)) {
				FSDataInputStream is = hdfs.open(path);
				// get the file info to create the buffer
				FileStatus stat = hdfs.getFileStatus(path);
				// create the buffer
				byte[] buffer = new byte[Integer.parseInt(String.valueOf(stat.getLen()))];
				is.readFully(0, buffer);
				is.close();

				return buffer;
			}
		} catch (Exception e) {
			log.error("HDFS 读取文件失败:" + e.getLocalizedMessage());
		} finally {
			if (hdfs != null) {
				try {
					hdfs.close();
				} catch (IOException e) {
					log.error("HDFS 关闭文件系统失败:" + e.getLocalizedMessage());
				}
			}
		}

		return null;
	}

	public static boolean readHDFSAndWriteAsFile(String hdfsPath, String localPath) {
		FileSystem localFS = null;
		FileSystem hdfs = null;
		try {
			localFS = FileSystem.getLocal(new Configuration());
			hdfs = FileSystem.get(conf);

			Path path = getPath(hdfsPath);
			FileOutputStream out = new FileOutputStream(localPath + "/" + path.getName());
			FSDataInputStream in = hdfs.open(path);

			IOUtils.copyBytes(in, out, 2048);

			in.close();
			out.close();
			return true;
		} catch (Exception e) {
			log.error("HDFS 读取文件失败:" + e.getLocalizedMessage());
		} finally {
			if (hdfs != null) {
				try {
					hdfs.close();
				} catch (IOException e) {
					log.error("HDFS 关闭文件系统失败:" + e.getLocalizedMessage());
				}
			}
			if (localFS != null) {
				try {
					localFS.close();
				} catch (IOException e) {
					log.error("HDFS 关闭文件系统失败:" + e.getLocalizedMessage());
				}
			}
		}

		return false;
	}

	public static boolean readHDFSAndWriteAsStream(String hdfsPath, OutputStream out) {
		FileSystem hdfs = null;
		FSDataInputStream in = null;
		try {
			hdfs = FileSystem.get(conf);

			Path path = getPath(hdfsPath);
			in = hdfs.open(path);

			IOUtils.copyBytes(in, out, 2048);

			return true;
		} catch (Exception e) {
			log.error("HDFS 读取文件失败:" + e.getLocalizedMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error("HDFS 关闭文件流失败:" + e.getLocalizedMessage());
				}
			}
			if (hdfs != null) {
				try {
					hdfs.close();
				} catch (IOException e) {
					log.error("HDFS 关闭文件系统失败:" + e.getLocalizedMessage());
				}
			}
		}
		return false;
	}

	public static InputStream getHDFSFileStream(String hdfsPath) {
		FileSystem hdfs = null;
		FSDataInputStream in = null;
		try {
			hdfs = FileSystem.get(conf);

			Path path = getPath(hdfsPath);
			in = hdfs.open(path);

			return hdfs.open(path);
		} catch (Exception e) {
			log.error("HDFS 读取文件失败:" + e.getLocalizedMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error("HDFS 关闭文件流失败:" + e.getLocalizedMessage());
				}
			}
			if (hdfs != null) {
				try {
					hdfs.close();
				} catch (IOException e) {
					log.error("HDFS 关闭文件系统失败:" + e.getLocalizedMessage());
				}
			}
		}
		return null;
	}

	public static long getHDFSFileLength(String hdfsPath) throws Exception {
		FileSystem hdfs = null;
		try {
			hdfs = FileSystem.get(conf);

			Path path = getPath(hdfsPath);
			if (hdfs.exists(path)) {
				FileStatus stat = hdfs.getFileStatus(path);
				return stat.getLen();
			}
		} catch (Exception e) {
			log.error("HDFS 读取文件大小失败:" + e.getLocalizedMessage());
		} finally {
			if (hdfs != null) {
				try {
					hdfs.close();
				} catch (IOException e) {
					log.error("HDFS 关闭文件系统失败:" + e.getLocalizedMessage());
				}
			}
		}

		return 0;
	}

	public static boolean isFileExist(String hdfsPath) throws Exception {
		FileSystem hdfs = null;
		try {
			hdfs = FileSystem.get(conf);

			Path path = getPath(hdfsPath);
			return hdfs.exists(path);
		} catch (Exception e) {
			log.error("HDFS 读取文件大小失败:" + e.getLocalizedMessage());
		} finally {
			if (hdfs != null) {
				try {
					hdfs.close();
				} catch (IOException e) {
					log.error("HDFS 关闭文件系统失败:" + e.getLocalizedMessage());
				}
			}
		}

		return false;
	}

	public static List<String> listAll(String dir) throws IOException {
		List<String> result = new ArrayList<String>();
		FileSystem hdfs = null;
		try {
			hdfs = FileSystem.get(conf);
			result.addAll(listAll(hdfs, dir));
		} catch (Exception e) {
			log.error("HDFS 读取文件失败:" + e.getLocalizedMessage());
		} finally {
			if (hdfs != null) {
				try {
					hdfs.close();
				} catch (IOException e) {
					log.error("HDFS 关闭文件系统失败:" + e.getLocalizedMessage());
				}
			}
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	private static List<String> listAll(FileSystem fs, String dir) throws IOException {
		List<String> result = new ArrayList<String>();

		FileStatus[] stats = fs.listStatus(getPath(dir));
		for (int i = 0; i < stats.length; ++i) {
			if (!stats[i].isDir()) {
				result.add(stats[i].getPath().toString());
			} else {
				// dir
				result.addAll(listAll(fs, stats[i].getPath().toString()));
			}
		}

		return result;
	}

	public static void main(String[] args) {
		// Configuration conf2 = new Configuration();
		// conf2.set("fs.defaultFS", "hdfs://aclomecluster");
		// // conf.set("fs.default.name", "hdfs://aclomecluster");
		// conf2.set("io.native.lib.available", "false");
		// conf2.set("dfs.nameservices", "aclomecluster");
		// conf2.set("dfs.ha.namenodes.aclomecluster", "nn1,nn2");
		// conf2.set("dfs.namenode.rpc-address.aclomecluster.nn1",
		// "hbase1:8020");
		// conf2.set("dfs.namenode.http-address.aclomecluster.nn1",
		// "hbase1:50070");
		// conf2.set("dfs.namenode.rpc-address.aclomecluster.nn2",
		// "hbase2:8020");
		// conf2.set("dfs.namenode.http-address.aclomecluster.nn2",
		// "hbase2:50070");
		// conf2.set("dfs.client.failover.proxy.provider.aclomecluster",
		// "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
		// conf2.set("dfs.ha.automatic-failover.enabled.aclomecluster", "true");
		//
		// FileSystem fs = null;
		// try {
		//
		// Path dstDir = new Path("hdfs://hbase1:9000");
		// // fs = FileSystem
		// // .get(new URI("hdfs://aclomecluster"), conf2, "hbase");
		// fs = FileSystem.get(conf2);
		// // fs = dstDir.getFileSystem(conf);
		// FileStatus[] list = fs.listStatus(new Path(
		// "hdfs://aclomecluster/hbase/"));
		// for (FileStatus file : list) {
		// System.out.println(file.getPath().getName());
		// }
		// fs.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		//
		// }
	}
}
