package brickstream.utilities;

/**
 * Utilities for File manipulation.
 *
 * This class has been <b>substantially</b> modified from the 
 * <a href="http://ostermiller.org/utils/">Ostermiller Java Utilities package</a>
 * More information about this class is available from <a target="_top" href=
 * "http://ostermiller.org/utils/FileHelper.html">ostermiller.org</a>.
 * 
 * Static File routines.
 * Copyright (C) 2002 Stephen Ostermiller
 * http://ostermiller.org/contact.pl?regarding=Java+Utilities
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * See COPYING.TXT for details.
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import brickstream.exception.SmartCameraInternalException;



public class FileHelper 
{
	public static final String FILE_SEPARATOR = 
										System.getProperty("file.separator");
	public static final String LINE_SEPARATOR = 
										System.getProperty("line.separator");
	
	public static final int FILENAME_FILTER_TYPE_STARTS_WITH = 1;
	public static final int FILENAME_FILTER_TYPE_CONTAINS = 2;
	
	
	/**
	 * Buffer size when reading from input stream.
	 */
	private static final int BUFFER_SIZE = 2048;

	private static class NameFilter implements FilenameFilter
	{
		String iFilterString;
		int iFilterType;
			
		NameFilter(String theFilterString, int theFilterType)
		{
			iFilterString = theFilterString;
			iFilterType = theFilterType;
		}
		
		/**
		 * Return true for all files having the specified date.
		 */
		public boolean accept(File dir, String name) 
		{
			boolean value = false;
			if (iFilterType == FILENAME_FILTER_TYPE_STARTS_WITH)
				value = (name.startsWith(iFilterString)) ? true : false;
			else if (iFilterType == FILENAME_FILTER_TYPE_CONTAINS)
				value = (name.indexOf(iFilterString) >= 0) ? true : false;
			
			return value;
		}
	}

	
	/**
	 * Append the data from one file to another.
	 * If there are multiple source files, use 
	 * <code>{@link #append(File[], File)}</code>
	 *
	 * @param from file from which the data should be copied.
	 * @param to file to which the data is to be appended.
	 * @throws IOException if an error occurs.
	 */
	public static void append(File from, File to) 
		throws IOException 
	{
		OutputStream os = null; 
		
		try {
	
			// Append to output file.
			// JDK 1.4
			//os = new BufferedOutputStream(new FileOutputStream(to, true));
			os = new BufferedOutputStream(new FileOutputStream(
												to.getCanonicalPath(), true));
			append(from, os);
	
		} finally {
			try {
				if (os != null)
					os.flush();
					os.close();
			} catch (IOException e ) {
				System.err.println("Unable to close file '" + to + "'");
			}
		}
	}


	/**
	 * Append the data from the file to the output stream.
	 *
	 * @param from file from which the data should be copied.
	 * @param os the output straem to which the data is to be appended.
	 * @throws IOException if an error occurs.
	 * 
	 * @see #append(File, File)
	 * @see #append(File[], File)
	 */
	private static void append(File from, 
							   OutputStream os)
		throws IOException 
	{
		InputStream is = null;

		try {
			is = new BufferedInputStream(new FileInputStream(from));
			byte[] buf = new byte[BUFFER_SIZE];
			int len;
			while ((len = is.read(buf)) != -1) {
				os.write(buf, 0, len);
			}
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException ignore) {
				System.err.println("Unable to close file '" + from + "'");
			}
		}
	}
	
	
	/**
	 * Append the data from multiple files to another.
	 * If there is only one source file, use 
	 * <code>{@link #append(File, File)}</code>
	 *
	 * @param from An array of Files from which the data should be copied.
	 * @param to file to which the data is to be appended.
	 * @throws IOException if an error occurs.
	 */
	public static void append(File[] from, File to) 
		throws IOException 
	{
		OutputStream os = null; 
		
		try {
	
			// Append to output file.
			// JDK 1.4
			//os = new BufferedOutputStream(new FileOutputStream(to, true));
			os = new BufferedOutputStream(new FileOutputStream(
												to.getCanonicalPath(), true));
			for (int i = 0; i < from.length; i++) {
				append(from[i], os);
			}
	
		} finally {
			try {
				if (os != null)
					os.flush();
					os.close();
			} catch (IOException e ) {
				System.err.println("Unable to close file '" + to + "'");
			}
		}
	}
	
	
	/**
	 * Checks if the specified file can be accessed.  If the file can't be 
	 * accessed, an error is logged, if desired
	 * 
	 * @param theFile the file to be checked for access
	 * @return <code>true</code> if it can be accessed.  Else <code>false</code>
	 */
	public static boolean canAccessFile(File theFile, boolean logError)
	{
		boolean canAccess = theFile.exists();
		
		if ((! canAccess) && (logError == true)) {
			String msg = "Unable to access '" + theFile.getAbsolutePath() + "'";
			System.err.println(msg);
		}
		
		return canAccess;
	}	


	/**
	 * Copy a file from one location to another.  An attempt is made to rename
	 * the file and if that fails, the file is copied.
	 *
	 * If the destination file already exists, an exception will be thrown.
	 *
	 * @param from file which should be copied.
	 * @param to desired destination of the file.
	 * @throws IOException if an error occurs.
	 */
	public static void copy(File from, File to) 
		throws IOException 
	{
		copy(from, to, false);
	}
	
	
	/**
	 * Copy a file from one location to another.  An attempt is made to rename
	 * the file and if that fails, the file is copied.
	 *
	 * @param from file which should be copied.
	 * @param to desired destination of the file.
	 * @param overwrite If false, an exception will be thrown rather than 
	 * overwrite a file.
	 * @throws IOException if an error occurs.
	 */
	public static void copy(File from, File to, boolean overwrite) 
		throws IOException 
	{
		// Copy.  Don't delete source.
		copyDelete(from, to, overwrite, false);
	}
	
	
	/**
	 * Copy the data from the input stream to the output stream.
	 *
	 * @param in data source
	 * @param out data destination
	 * @throws IOException in an input or output error occurs
	 */
	private static void copy(InputStream in, OutputStream out) 
		throws IOException 
	{
		byte[] buffer = new byte[BUFFER_SIZE];
		int read;
		while((read = in.read(buffer)) != -1){
			out.write(buffer, 0, read);
		}
	}
		
	
	/**
	 * Copy a file from one location to another and delete source if
	 * specified.
	 * <p>
	 * If the source is not to be deleted, the file is copied
	 * <p>
	 * If the source is to be deleted, an attempt is made to rename 
	 * the file and if that fails, the file is copied and the old file deleted.
	 * 
	 * @param from file which should be copied/moved.
	 * @param to desired destination of the file.
	 * @param overwrite If false, an exception will be thrown rather 
	 * than overwrite a file.
	 * @param deleteSource deletes the source file if required after 
	 * doing the copying
	 * @throws IOException if an error occurs.
	 */
	private static void copyDelete(File from, 
								   File to, 
								   boolean overwrite,
								   boolean deleteSource)
		throws IOException 
	{
		if (from.isDirectory())
			throw new IOException("Invalid argument.  '" + from.toString() + 
								  "' is a directory.");
		
		if (to.isDirectory())
			throw new IOException("Invalid argument.  '" + to.toString() + 
								  "' is a directory.");
		
		if (to.exists()){
			if (overwrite){
				if (!to.delete()){
					String msg = "Unable to delete existing destination " + 
								 "file '" + to.toString() + "'";
					throw new IOException(msg);
				}
			} else {
				String msg = "File '" + to.toString() + "' already exists.";
				throw new IOException(msg);
			}
		}

		if (deleteSource && (from.renameTo(to))) 
			return;

		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(from));
			out = new BufferedOutputStream(new FileOutputStream(to));
			copy(in, out);
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
			if ((deleteSource) && (!from.delete())){
				String msg = "Unable to delete source file '" + 
							 from.toString() + "'";
				throw new IOException(msg);
			}
		} finally {
			if (in != null){
				try {
					in.close();
				} catch (IOException ignore){
				}
				in = null;
			}
			if (out != null){
				try {
					out.flush();
					out.close();
				} catch (IOException ignore){
				}
				out = null;
			}
		}
	}
	

	/**
	 * Copy a file from one location to a directory.  The filename is set to 
	 * the source file name.  An attempt is made to rename the file and if 
	 * that fails, the file is copied.
	 *
	 * @param from file which should be copied.
	 * @param dir the directory in which the file is to be copied.
	 * @param overwrite If false, an exception will be thrown rather than
	 * overwrite a file.
	 * @throws IOException if an error occurs.
	 * @throws IllegalArgumentException if the <code>dir</code> is not a 
	 * directory.
	 * 
	 * @see #copyToDir(File[], File, boolean)
	 */
	public static void copyToDir(File from, File dir, boolean overwrite) 
		throws IOException, IllegalArgumentException
	{
		if (!dir.isDirectory())	{
			String msg = "Invalid argument.  '" + dir + "' is not a directory.";
			System.err.println(msg);
			throw new IllegalArgumentException(msg);
		}
		
		// Create the destination file
		File to = new File(dir.getAbsolutePath() + 
		                   System.getProperty("file.separator") + 
		                   from.getName());
		                   
		// Copy.  Don't delete source.
		copyDelete(from, to, overwrite, false);
	}
	
	
	/**
	 * Copy files from one location to a directory.  The filename is set to 
	 * the source file name.  An attempt is made to rename the files and if 
	 * that fails, the files are copied.
	 *
	 * @param from files that are to be copied.
	 * @param dir the directory in which the files are to be copied.
	 * @param overwrite If false, an exception will be thrown rather than
	 * overwrite a file.
	 * @throws IOException if an error occurs.
	 * @throws IllegalArgumentException if the <code>dir</code> is not a 
	 * directory.
	 * 
	 * @see #copyToDir(File, File, boolean)
	 */
	public static void copyToDir(File[] from, File dir, boolean overwrite) 
		throws IOException, IllegalArgumentException
	{
		if (!dir.isDirectory())	{
			String msg = "Invalid argument.  '" + dir + "' is not a directory.";
			System.err.println(msg);
			throw new IllegalArgumentException(msg);
		}
		
		String fromPrefix = dir.getAbsolutePath() + 
						    System.getProperty("file.separator");
		
		for (int i = 0; i < from.length; i++) {
			// Create the destination file
			File to = new File(fromPrefix + from[i].getName());
			                   
			// Copy.  Don't delete source.
			copyDelete(from[i], to, overwrite, false);
		}
	}
	
	
	/**
	 * Creates the specified directory.  If the directory already
	 * exists, and it is to be recreated, the existing directory is
	 * deleted and then recreated.
	 * 
	 * @param theDirName the name of the directory to be created
	 * @param recreate If <code>true</code> and the directory 
	 * already exists, it is deleted and recreated.
	 * @return the <code>File</code> object for the directory created
	 * @throws EmteraInternalException if an error occurs.
	 */
	public static File createDir(String theDirName, boolean recreate)
		throws SmartCameraInternalException
	{
		File theDir = new File(theDirName);
		
		// If it already exists, delete it if we have to recreate it.
		boolean exists = theDir.exists();
		if (exists && recreate) {
			deleteDir(theDir);
			exists = false;
		}
			
		if (!exists) {
			if (!theDir.mkdirs()) {
				// Check if it exists again since it may have been created
				// between the time we checked and attempted to create it.
				// Possibly created in another thread.
				if (!(theDir.exists())) {
					String msg = "Unable to create directory '" + theDirName + "'";
					System.err.println(msg);
					throw new SmartCameraInternalException(msg);
				}
			}
		}
				
		return theDir;
	}
	

	/**
	 * Deep deletes the files and directories in the specified 
	 * directory, but does not delete the directory itself.  
	 * If the directory has to be deleted as well, use the 
	 * <code>deleteDir</code> method.
	 * 
	 * @param theDir the directory whose contents are to be deleted.
	 * @throws EmteraInternalException if an error occurs.
	 * 
	 * @see #deleteDir(File)
	 */
	public static void deleteContentsOf(File theDir)
		throws SmartCameraInternalException 
	{
		if (theDir.isDirectory()) {

			File[] theFiles = theDir.listFiles();
			
			for (int i = 0; i < theFiles.length; i++) {
			
				File currFile = theFiles[i];
				if (currFile.isDirectory()) {
					deleteDir(currFile);
				} else {		
					if (! currFile.delete()) {
						String msg = "Unable to delete file '" + currFile + "'";
						System.err.println(msg);
						throw new SmartCameraInternalException(msg);
					}
				}
			}
		}
	}		


	/**
	 * Deep deletes the specified directory.
	 * If the contents of the directory are to be deleted, but the
	 * directory itself is not to be deleted, use the
	 * <code>deleteContentsOf</code> method.
	 * 
	 * @param theDir the directory to be deleted.
	 * @throws EmteraInternalException if an error occurs.
	 * 
	 * @see #deleteContentsOf(File)
	 */
	public static void deleteDir(File theDir)
		throws SmartCameraInternalException
	{
		// Check if it exists.
		if (theDir.exists()) {

			// Delete any existing files (including directories).
			deleteContentsOf(theDir);

			// Now delete the directory			
			if (! theDir.delete()) {
				String msg = "Unable to delete directory '" + theDir + "'";
				System.err.println(msg);
				throw new SmartCameraInternalException(msg);
			}
		}	
	}

	
	/**
	 * Deletes all files from the specified directory that match the specified
	 * filter.  Valid filter types are defined as 
	 * <code>FILENAME_FILTER_TYPE_XXX</code> public constants in this class.
	 * 
	 * <br><b>
	 * If there is an error while deleting any of the files, the error is 
	 * logged, but no exception is thrown.  Also, this is not an atomic 
	 * operation.  Some files may get deleted, but others may not due to an
	 * error.   
	 * </b>
	 * 
	 * @param theSourceDir the directory from which the files are to be deleted
	 * @param theFilterString the string to be used to compare with the filename
	 * @param theFilterType the type of comparisson to be done with the file name.
	 */
	public static void deleteFiles(File theSourceDir, 
								   String theFilterString, 
								   int theFilterType)
	{
		NameFilter theFileFilter = new NameFilter(theFilterString, theFilterType);
		File[] theFiles = theSourceDir.listFiles(theFileFilter);
		deleteFiles(theFiles);
	}


	/**
	 * Deletes the specified files.  
	 * 
	 * <br><b>
	 * If there is an error while deleting any of the files, the error is 
	 * logged, but no exception is thrown.  Also, this is not an atomic 
	 * operation.  Some files may get deleted, but others may not due to an
	 * error.   
	 * </b>
	 * 
	 * @param theFiles the files to be deleted.
	 */
	public static void deleteFiles(File[] theFiles) 
	{
		if (theFiles != null) {
			for (int i = 0; i < theFiles.length; i++) {
				boolean deleted = theFiles[i].delete();
				if (!deleted) {
					String msg = "Unable to delete '" + theFiles[i] + "'";
					System.err.println(msg);
					//throw new EmteraInternalException(msg);
				}
			}
		}
	}

	/**
	 * List all of the files in a given directory and its sub-directories.
	 * 
	 * @param file File object representing the directory to list
	 * 
	 * @return List of File objects contained in the directory
	 */
	public static ArrayList listAllFiles(File file) {
			ArrayList files = new ArrayList();
			if (file != null && file.exists() && file.isDirectory()) {
					File[] tmpFiles = file.listFiles();
					files.addAll(Arrays.asList(tmpFiles));
					for (int i=0; i < tmpFiles.length; i++) {
							File f = tmpFiles[i];
							files.addAll(listAllFiles(f));
					}
			}
			return files;
	}
	
	/**
	 * Move a file from one location to another.  An attempt is made to rename
	 * the file and if that fails, the file is copied and the old file deleted.
	 *
	 * If the destination file already exists, an exception will be thrown.
	 *
	 * @param from file which should be moved.
	 * @param to desired destination of the file.
	 * @throws IOException if an error occurs.
	 */
	public static void move(File from, File to) 
		throws IOException 
	{
		move(from, to, false);
	}

	
	/**
	 * Move a file from one location to another.  An attempt is made to rename
	 * the file and if that fails, the file is copied and the old file deleted.
	 *
	 * @param from file which should be moved.
	 * @param to desired destination of the file.
	 * @param overwrite If false, an exception will be thrown rather than 
	 * overwrite a file.
	 * @throws IOException if an error occurs.
	 */
	public static void move(File from, File to, boolean overwrite) 
		throws IOException 
	{
		// Move.  Delete source when done copying
		copyDelete(from, to, overwrite, true);
	}


	/**
	 * Concatenates the filename with the directory name and returns
	 * the fully qualified file name.  This method adds a directory
	 * seperator between the directory and the filename if it doesn't exist.
	 * <p>
	 * The filename could also be another directory name.  In which case this
	 * method will build a fully qualified directory without a trailing '/'.
	 * <p> 
	 * If more than one directory is to be concatenated, use the 
	 * <code>{@link #buildFullyQualifiedFileName(String[])}</code> method.
	 * <p>
	 * The following examples show the returned string with the passed arguments
	 * <code>
	 * <table border = 1>
	 * <tr><td><center>Passed<td><center>Returned</tr>
	 * <tr><td>"", ""<td>"\"</tr>
	 * <tr><td>"dir", ""<td>"dir\"</tr>
	 * <tr><td>"", "file"<td>"\file"</tr>
	 * <tr><td>"\\", "/"<td>"\"</tr>
	 * <tr><td>"dir", "file"<td>"dir\file"</tr>
	 * <tr><td>"dir/", "/file/"<td>"dir\file"</tr>
	 * <tr><td>"dir\\", "file"<td>"dir\file"</tr>
	 * <tr><td>"dir", "file\\"<td>"dir\file"</tr>
	 * <tr><td>"/\\//\\/s/\\\\//", "file\\"<td>"s\file"</tr>
	 * <tr><td>"/\\//\\/s/\\\\//", "//There \\ was / never\\\\"<td>"s\There \ was \ never"</tr>
	 * </table>
	 * </code>
	 *
	 * @param theDir - the name of the directory
	 * @param theFileName - the name of the file or directory.
	 * @return the fully qualified file name.
	 */
	public static String buildFullyQualifiedFileName(String theDir,
		                                             String theFileName)
	{
		String theFQFileName = FileHelper.FILE_SEPARATOR;
	
		theDir = (theDir == null) ? "" : FileHelper.trim(theDir, "/\\");
		theFileName = FileHelper.trim(theFileName, "/\\");
		theFQFileName = theDir + FileHelper.FILE_SEPARATOR + theFileName;
		theFQFileName = theFQFileName.replace('\\', 
										FileHelper.FILE_SEPARATOR.charAt(0));
		theFQFileName = theFQFileName.replace('/', 
										FileHelper.FILE_SEPARATOR.charAt(0));
	
		return theFQFileName;
	}


	/**
	 * Concatenates the array of names and returns
	 * the fully qualified file name.  This method adds a directory
	 * seperator between each name in the array if it doesn't already exist.
	 * <p>
	 * The last name in the array could be a filename or another directory name.  
	 * If it's a directory name, a fully qualified directory name 
	 * without a trailing file seperator will be returned.
	 * <p> 
	 * If there is only one directory and one filename, use the 
	 * <code>{@link #buildFullyQualifiedFileName(String, String)</code> method.
	 * <p>
	 * The following examples show the returned string with the passed arguments
	 * <code>
	 * <table border = 1>
	 * <tr><td><center>Passed<td><center>Returned</tr>
	 * <tr><td>"", "", "", ""<td>"\"</tr>
	 * <tr><td>"dir1", "dir2", "dir3", "file"<td>"dir1\dir2\dir3\file"</tr>
	 * <tr><td>"\\", "/", "dir3"<td>"dir3"</tr>
	 * <tr><td>"dir1/", "dir2\\", "/file/"<td>"dir1\dir2\file"</tr>
	 * <tr><td>"dir1/", "\\", "/file/"<td>"dir1\file"</tr>
	 * <tr><td>"dir1/", "", "/file/"<td>"dir1\file"</tr>
	 * </table>
	 * </code>
	 *
	 * @param theNames an array of directory names.
	 * @return the fully qualified file name.
	 */
	public static String buildFullyQualifiedFileName(String[] theNames)
	{
		String theFQFileName = FileHelper.FILE_SEPARATOR;
	
		int count = theNames.length;
		if (count > 0) {
			StringBuffer sb = new StringBuffer(500);
			String currName;
			for (int i = 0; i < count; i++) {
				currName = FileHelper.trim(theNames[i], "/\\"); 
				if (currName.length() > 0) {
					sb.append(currName);
					sb.append(FileHelper.FILE_SEPARATOR);
				}
			}
			// Handle the case when there were all blank strings
			if (sb.length() == 0) {
				theFQFileName = FileHelper.FILE_SEPARATOR;
			} else {
				theFQFileName = sb.substring(0, sb.length() - 1);
				theFQFileName = theFQFileName.replace('\\', 
										FileHelper.FILE_SEPARATOR.charAt(0));
				theFQFileName = theFQFileName.replace('/', 
										FileHelper.FILE_SEPARATOR.charAt(0));
			}
		}
	
		return theFQFileName;
	}
	
	
	/**
	 * Concatenates the machine name and the array of names and returns
	 * the UNC in the form <code>\\machine name\name1\name2...</code>.  
	 * <p>
	 * The returned name will not have a trailing file <code>'\'</code>.  
	 * <p> 
	 * To build a fully qualified filename, use the one of the 
	 * <code>buildFullyQualifiedFileName</code> methods. 
	 *
	 * @param theMachineName the name of the machine
	 * @param theNames an array of directory names.
	 * @return the fully qualified file name.
	 */
	public static String buildUNC(String theMachineName, String[] theNames)
	{
		String theUNC = "\\\\" + theMachineName;
		StringBuffer sb = new StringBuffer(500);
		sb.append("\\\\")
		  .append(theMachineName);
	
		int count = theNames.length;
		if (count > 0) {
			String currName;
			for (int i = 0; i < count; i++) {
				currName = FileHelper.trim(theNames[i], "/\\"); 
				if (currName.length() > 0) {
					sb.append("\\");
					sb.append(currName);
				}
			}
		}
	
		return sb.toString();
	}	

	/**
	 * Read the contents of the specified file and return it as a string. 
	 * Errors are logged.
	 * 
	 * @param theFile the file whose contents are to be read
	 * 
	 * @return the contents of the file.
	 */
	public static String readFile(File theFile) 
	{
		StringBuffer sb = new StringBuffer(1000);
		BufferedReader br = null;
	
		try {
			br = new BufferedReader(new FileReader(theFile));
			String currLine;
			while ((currLine = br.readLine()) != null) {
				 sb.append(currLine).append("\n");
			}
		} catch (FileNotFoundException e) {
			String msg = "Unable to find file '" + 
						 theFile.getAbsolutePath() + "' - " + e;
			System.err.println(msg);
			
		} catch (IOException e) {
			String msg = "Error while trying to read file '" + 
						 theFile.getAbsolutePath() + "' - " + e;
			System.err.println(msg);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				String msg = "Unable to close file '" + 
							 theFile.getAbsolutePath() + "'";
				System.err.println(msg);
			}
		}
	
		return sb.toString();
	}


	/**
	 * Read the file and return all lines as an ArrayList.
	 *   
	 * @return an ArrayList of lines in the file.
	 */
	public static ArrayList readLines(File theFile) 
	{
		ArrayList theLines = new ArrayList();
		BufferedReader br = null;
		
		try {
			
			br = new BufferedReader(new FileReader(theFile));
			String currLine;
			while ((currLine = br.readLine()) != null) {
				theLines.add(currLine);
			}
			
		} catch (FileNotFoundException e) {
			String msg = "Unable to find the file '" + 
						 theFile.getAbsolutePath() + "'";
			System.err.println(msg);
		
		} catch (IOException e) {
			String msg = "Unable to read from the file '" + 
						 theFile.getAbsolutePath() + "'";
			System.err.println(msg);

		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				String msg = "Unable to close the file '" + 
							 theFile.getAbsolutePath() + "'";
				System.err.println(msg);
			}
		}
		
		return theLines;
	}		
	
    /**
     * Returns a copy of the string, with leading and trailing characters
     * in the specified <code>filter</code> string removed.
     * <p>
     * If there are no leading and trailing characters that match the filter
     * criteria, the passed string itself is returned.
     *
     * @param theStr the string to be trimmed
     * @param theFilter the string containing characters to be removed.
     *
     * @return  A copy of this string with leading and trailing specified
     *          characters removed, or this string if it has no leading or
     *          trailing characters.
     */
    public static String trim(String theStr, String theFilter) {
        int len = theStr.length();
        int st = 0;
        char[] val = theStr.toCharArray();
        char[] filt = theFilter.toCharArray();

        // Trim leading
        for (int i = 0; ((i < filt.length) && (st < len)); i++) {
            if (val[st] == filt[i]) {
                st++;
                i = -1;
            }
        }

        // Trim Trailing
        for (int i = 0; ((i < filt.length) && (st < len)); i++) {
            if (val[len - 1] == filt[i]) {
                len--;
                i = -1;
            }
        }

        return ((st > 0) || (len < theStr.length()))
        ? theStr.substring(st, len) : theStr;
    }

}