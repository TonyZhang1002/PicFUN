package com.example.zhang.picfun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/*
    Here is the FTP connecting utility class
    @author zhang
 */

public class FTPutils {

    private FTPClient ftpClient = null;
    private static FTPutils ftpUtilsInstance = null;
    private String FTPUrl;
    private int FTPPort;
    private String UserName;
    private String UserPassword;

    private FTPutils()
    {
        ftpClient = new FTPClient();
    }

    /*
    Here we get the instance of this class
    @author zhang
     */
    public  static FTPutils getInstance() {
        if (ftpUtilsInstance == null)
        {
            ftpUtilsInstance = new FTPutils();
        }
        return ftpUtilsInstance;
    }

    /*
    Here we init the FTP setting
    @param FTPUrl       FTP server ip
    @param FTPPort       FTP server port
    @param UserName       FTP service user name
    @param UserPassword       FTP service user password
    @return
    @author zhang
     */
    public boolean initFTPSetting(String FTPUrl, int FTPPort, String UserName, String UserPassword)
    {
        this.FTPUrl = FTPUrl;
        this.FTPPort = FTPPort;
        this.UserName = UserName;
        this.UserPassword = UserPassword;

        int reply;

        try {
            //1. connect server
            ftpClient.connect(FTPUrl, FTPPort);

            //2. login
            ftpClient.login(UserName, UserPassword);

            //3. if reply equals 230
            reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply))
            {
                //disconnect
                ftpClient.disconnect();
                return false;
            }

            return true;

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /*
    Here we create an upload file method
    @param FilePath      the upload file's path
    @param FileName      the file's name
    @return         true is success
    @author zhang
     */
    public boolean uploadFile(String FilePath, String FileName) {

        if (!ftpClient.isConnected())
        {
            if (!initFTPSetting(FTPUrl,  FTPPort,  UserName,  UserPassword))
            {
                return false;
            }
        }

        try {

            //set the storing directory
            ftpClient.makeDirectory("/data");
            ftpClient.changeWorkingDirectory("/data");

            //set some basic upload settings
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            //upload the file
            FileInputStream fileInputStream = new FileInputStream(FilePath);
            ftpClient.storeFile(FileName, fileInputStream);

            //close the input stream
            fileInputStream.close();

            //quit the ftp server and logout
            ftpClient.logout();
            ftpClient.disconnect();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /*
    Here we create an download file method
    @param FilePath      the download file's storing directory
    @param FileName      the file's name in the remote server
    @return         true is success
    @author zhang
     */
    public boolean downLoadFile(String FilePath, String FileName) {

        if (!ftpClient.isConnected())
        {
            if (!initFTPSetting(FTPUrl,  FTPPort,  UserName,  UserPassword))
            {
                return false;
            }
        }

        try {
            // to the working directory
            ftpClient.changeWorkingDirectory("/data");

            ftpClient.enterLocalPassiveMode();

            // list all the files there
            FTPFile[] files = ftpClient.listFiles();

            // for all the files, get the correct one
            for (FTPFile file : files) {
                if (file.getName().equals(FileName)) {
                    //init the file according to the file's path
                    File localFile = new File(FilePath);

                    // set the output stream
                    OutputStream outputStream = new FileOutputStream(localFile);

                    // download the files
                    ftpClient.retrieveFile(file.getName(), outputStream);

                    // close the stream
                    outputStream.close();
                }
            }

            // logout and disconnect
            ftpClient.logout();
            ftpClient.disconnect();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

}
