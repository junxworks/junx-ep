/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  FileRepository.java   
 * @Package io.github.junxworks.ep.fs.driver   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-8-2 13:58:55   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.fs.driver;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import io.github.junxworks.junx.core.lifecycle.Lifecycle;

/**
 * 文件仓库，支持存储所有非结构化数据。只负责底层文件存储与获取，不负责业务逻辑处理，即业务需要自己处理文件名、扩展类型、mime-type映射等等。
 *
 * @ClassName:  FileRepository
 * @author: Michael
 * @date:   2019-1-6 10:58:10
 * @since:  v1.0
 */
public interface FileRepository extends Lifecycle {

	static final String TOKEN_SEP = "/";

	/**
	 * 存储文件，并且返回文件token，给业务进行存储，token生成的规则不因内部存储驱动而改变，
	 * 因此业务可以根据实际情况修改底层存储驱动
	 *
	 * @param file the file
	 * @return 文件token，用于从文件仓库获取文件数据
	 * @throws StoreFailedException the store file failed exception
	 */
	public String storeFile(File file) throws StoreFailedException;

	/**
	 * 存储文件，并且返回文件token，给业务进行存储，token生成的规则不因内部存储驱动而改变，
	 * 因此业务可以根据实际情况修改底层存储驱动
	 *
	 * @param bytes the bytes
	 * @return 文件token，用于从文件仓库获取文件数据
	 * @throws StoreFailedException the store file failed exception
	 */
	public String storeFile(byte[] bytes) throws StoreFailedException;

	/**
	 * 存储文件，并且返回文件token，给业务进行存储，token生成的规则不因内部存储驱动而改变，
	 * 因此业务可以根据实际情况修改底层存储驱动
	 *
	 * @param inputStream the input stream
	 * @return 文件token，用于从文件仓库获取文件数据
	 * @throws StoreFailedException the store file failed exception
	 */
	public String storeFile(InputStream inputStream) throws StoreFailedException;

	/**
	 * 根据文件token获取一个文件字节数组
	 *
	 * @param storageId 文件存储时候返回的文件token
	 * @return the file
	 * @throws FetchFailedException the fetch file failed exception
	 */
	public byte[] fetchFileBytes(String storageId) throws FetchFailedException;

	/**
	 * 根据文件token获取一个文件，并且将其写入到指定的output流中
	 *
	 * @param storageId 文件存储时候返回的文件token
	 * @param output the output
	 * @throws FetchFailedException the fetch file failed exception
	 */
	public void fetchFileIntoStream(String storageId, OutputStream output) throws FetchFailedException;

	/**
	 * 根据文件token获取一个文件，并且返回输入流
	 * 【注意】输入流用完必须关闭，不然会造成资源泄漏
	 *
	 * @param storageId 文件存储时候返回的文件token
	 * @param output the output
	 * @throws FetchFailedException the fetch file failed exception
	 */
	public InputStream fetchFileAsStream(String storageId) throws FetchFailedException;

}
