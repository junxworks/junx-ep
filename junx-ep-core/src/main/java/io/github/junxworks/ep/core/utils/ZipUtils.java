package io.github.junxworks.ep.core.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.tools.zip.ZipFile;

public class ZipUtils {
	private static final int BUFFER_SIZE = 10 * 1024;

	public static void zipDir(File srcDir, OutputStream out, boolean KeepDirStructure) throws RuntimeException {
		try (ZipOutputStream zos = new ZipOutputStream(out)) {
			compress(srcDir, zos, srcDir.getName(), KeepDirStructure);
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils", e);
		}
	}

	/**
	 * 压缩成ZIP 方法1
	 * 24
	 *
	 * @param srcDir           压缩文件夹路径
	 * @param out              压缩文件输出流
	 * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
	 *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws RuntimeException 压缩失败会抛出运行时异常
	 */
	public static void zipDir(String srcDir, OutputStream out, boolean KeepDirStructure) throws RuntimeException {
		File sourceDir = new File(srcDir);
		zipDir(sourceDir, out, KeepDirStructure);
	}

	/**
	 * 压缩成ZIP 方法2
	 *
	 * @param srcFiles 需要压缩的文件列表
	 * @param out      压缩文件输出流
	 * @throws RuntimeException 压缩失败会抛出运行时异常
	 */
	public static void zipFile(List<File> srcFiles, OutputStream out) throws RuntimeException {
		try (ZipOutputStream zos = new ZipOutputStream(out)) {
			for (File srcFile : srcFiles) {
				byte[] buf = new byte[BUFFER_SIZE];
				zos.putNextEntry(new ZipEntry(srcFile.getName()));
				int len;
				try (FileInputStream in = new FileInputStream(srcFile)) {
					while ((len = in.read(buf)) != -1) {
						zos.write(buf, 0, len);
					}
				} finally {
					zos.closeEntry();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils", e);
		}
	}

	/**
	 * 递归压缩方法
	 *
	 * @param sourceFile       源文件
	 * @param zos              zip输出流
	 * @param name             压缩后的名称
	 * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
	 *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws Exception
	 */
	private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure) throws Exception {
		byte[] buf = new byte[BUFFER_SIZE];
		if (sourceFile.isFile()) {
			// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
			zos.putNextEntry(new ZipEntry(name));
			// copy文件到zip输出流中
			int len;
			try (FileInputStream in = new FileInputStream(sourceFile)) {
				while ((len = in.read(buf)) != -1) {
					zos.write(buf, 0, len);
				}
			} finally {
				zos.closeEntry();
			}
		} else {
			File[] listFiles = sourceFile.listFiles();
			if (listFiles == null || listFiles.length == 0) {
				// 需要保留原来的文件结构时,需要对空文件夹进行处理
				if (KeepDirStructure) {
					// 空文件夹的处理
					zos.putNextEntry(new ZipEntry(name + "/"));
					// 没有文件，不需要文件的copy
					zos.closeEntry();
				}
			} else {
				for (File file : listFiles) {
					// 判断是否需要保留原来的文件结构
					if (KeepDirStructure) {
						// 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
						// 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
						compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
					} else {
						compress(file, zos, file.getName(), KeepDirStructure);
					}
				}

			}

		}
	}

	/**
	 * 解压zip格式的压缩文件到指定位置
	 *
	 * @param zipFileName 压缩文件
	 * @param extPlace    解压目录
	 * @throws Exception
	 */
	public static void unZipFiles(String zipFileName, String extPlace) throws Exception {
		System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding"));
		(new File(extPlace)).mkdirs();
		File f = new File(zipFileName);
		try (ZipFile zipFile = new ZipFile(zipFileName, "GBK")) { // 处理中文文件名乱码的问题) 
			if ((!f.exists()) && (f.length() <= 0)) {
				throw new Exception("要解压的文件不存在!");
			}
			String strPath, gbkPath, strtemp;
			File tempFile = new File(extPlace);
			strPath = tempFile.getAbsolutePath();
			Enumeration<?> e = zipFile.getEntries();
			while (e.hasMoreElements()) {
				org.apache.tools.zip.ZipEntry zipEnt = (org.apache.tools.zip.ZipEntry) e.nextElement();
				gbkPath = zipEnt.getName();
				if (zipEnt.isDirectory()) {
					strtemp = strPath + File.separator + gbkPath;
					File dir = new File(strtemp);
					dir.mkdirs();
					continue;
				} else { // 读写文件

					gbkPath = zipEnt.getName();
					String strsubdir = gbkPath;
					strtemp = strPath + File.separator + gbkPath;// 建目录
					try (InputStream is = zipFile.getInputStream(zipEnt); BufferedInputStream bis = new BufferedInputStream(is); FileOutputStream fos = new FileOutputStream(strtemp); BufferedOutputStream bos = new BufferedOutputStream(fos);) {
						for (int i = 0; i < strsubdir.length(); i++) {
							if (strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {
								String temp = strPath + File.separator + strsubdir.substring(0, i);
								File subdir = new File(temp);
								if (!subdir.exists())
									subdir.mkdir();
							}
						}
						int c;
						while ((c = bis.read()) != -1) {
							bos.write((byte) c);
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
