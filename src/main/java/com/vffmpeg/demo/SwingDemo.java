package com.vffmpeg.demo;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SwingDemo extends JFrame {
    private static int allWidth=800;
    private static String ffmegPath="";
    private static JLabel JLabelVideo;
    private static JLabel JLabelMusic;
    private static JLabel JLabelOut;
    private static JLabel JLabelRemark;
    private JButton button ;
    private static String joinVideoPath="";
    private static String joinMusicPath="";
    private static String joinOutPath="";
    private String ffmpegEXE;
    public SwingDemo(String ffmpegEXE) {
        super();
        this.ffmpegEXE = ffmpegEXE;
    }
    public SwingDemo() {
        super();
    }
    /**
     * 上传视频
     *
     * @param button
     */
    public static void addVideo(JButton button) {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.setDialogTitle("选择合成视频");
        /** 过滤文件类型 * */
        FileNameExtensionFilter filter = new FileNameExtensionFilter("mp4,mkv", "mp4","mkv");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(button);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            /** 得到选择的文件* */
            File[] arrfiles = chooser.getSelectedFiles();
            if (arrfiles == null || arrfiles.length == 0) {
                return;
            }
            String joinVideo="";
            for(int i=0;i<arrfiles.length;i++){
                File file=arrfiles[i];
                String fileA=file.getAbsolutePath();
                System.out.println("fileA:"+fileA);
                joinVideo=joinVideo.equals("")?file.getName():joinVideo+","+file.getName();
                joinVideoPath=joinVideoPath.equals("")?file.getAbsolutePath():joinVideoPath+","+file.getAbsolutePath();
            }
            JLabelVideo.setText("视频文件:"+joinVideo);
            try {
                JOptionPane.showMessageDialog(null, "上传成功！", "提示",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "上传失败！", "提示",
                        JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            }
        }
    }
    public static void addMusic(JButton button) {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.setDialogTitle("选择合成音频");
        /** 过滤文件类型 * */
        FileNameExtensionFilter filter = new FileNameExtensionFilter("mp3,wav", "mp3","wav");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(button);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            /** 得到选择的文件* */
            File[] arrfiles = chooser.getSelectedFiles();
            if (arrfiles == null || arrfiles.length == 0) {
                return;
            }
            String joinMusic="";
            for(int i=0;i<arrfiles.length;i++){
                File file=arrfiles[i];
                String fileA=file.getAbsolutePath();
                System.out.println("fileA:"+fileA);
                joinMusic=joinMusic.equals("")?file.getName():joinMusic+","+file.getName();
                joinMusicPath=joinMusicPath.equals("")?file.getAbsolutePath():joinMusicPath+","+file.getAbsolutePath();
            }
            JLabelMusic.setText("音频文件:"+joinMusic);
            try {
                JOptionPane.showMessageDialog(null, "上传成功！", "提示",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "上传失败！", "提示",
                        JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            }
        }
    }
    public static void addOut(JButton button) {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("选择输出文件夹");
        int returnVal = chooser.showOpenDialog(button);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            /** 得到选择的文件* */
            File arrfiles = chooser.getSelectedFile();
            joinOutPath=arrfiles.getAbsolutePath();
            JLabelOut.setText("输出路径:"+arrfiles.getAbsolutePath());
            try {

                JOptionPane.showMessageDialog(null, "上传成功！", "提示",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "上传失败！", "提示",
                        JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            }
        }
    }
    public static void fit() {
        try {
            SwingDemo demo = new SwingDemo(ffmegPath);
            System.out.println("joinVideoPath:"+joinVideoPath);
            System.out.println("joinMusicPath:"+joinMusicPath);
            System.out.println("joinOutPath:"+joinOutPath);
            if (StrUtil.isEmpty(joinVideoPath)) {
                JOptionPane.showMessageDialog(null, "视频文件未上传！", "提示",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (StrUtil.isEmpty(joinMusicPath)) {
                JOptionPane.showMessageDialog(null, "音频文件未上传！", "提示",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (StrUtil.isEmpty(joinOutPath)) {
                JOptionPane.showMessageDialog(null, "输出文件夹未上传！", "提示",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
//            joinVideoPath="E:\\ffmpeg\\swing\\video\\陈晓强.mp4";
//            joinMusicPath="E:\\ffmpeg\\swing\\music\\陈晓强.wav";
//            joinOutPath="E:\\ffmpeg\\swing\\out";
            String[] videos = joinVideoPath.split(",");
            String[] musics = joinMusicPath.split(",");
            if(videos.length!=musics.length){
                JOptionPane.showMessageDialog(null, "视频数量必须和音频数量相等！", "提示",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            for (int i = 0; i < videos.length; i++) {
                demo.createMp4(videos[i], musics[i], joinOutPath);
            }
            JOptionPane.showMessageDialog(null, "全部合成完毕！", "提示",
                    JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e1){
            e1.printStackTrace();
        }
    }
    public void addComponentsToPane(Container pane) {
        // content pane默认是BorderLayout，因此这里可以省略
        pane.setLayout(new FlowLayout(FlowLayout.LEFT));
        button = new JButton("1、上传视频");
        button.setFont(new Font("宋体", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(250, 50));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addVideo(button);
            }
        });
        pane.add(button);
        button = new JButton("2、上传音频");
        button.setFont(new Font("宋体", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(250, 50));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addMusic(button);
            }
        });
        pane.add(button);
        button = new JButton("3、输出文件夹");
        button.setFont(new Font("宋体", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(250, 50));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addOut(button);
            }
        });
        pane.add(button);

        button = new JButton("4、开始合成文件");
        button.setFont(new Font("宋体", Font.BOLD, 25));
        button.setPreferredSize(new Dimension(allWidth-40, 80));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fit();
            }
        });
        pane.add(button);
        JLabelVideo = new JLabel("视频文件：");
        JLabelVideo.setFont(new Font("宋体", Font.BOLD, 18));
        JLabelVideo.setPreferredSize(new Dimension(allWidth, 80));
        pane.add(JLabelVideo);
        JLabelMusic = new JLabel("音频文件：");
        JLabelMusic.setFont(new Font("宋体", Font.BOLD, 18));
        JLabelVideo.setPreferredSize(new Dimension(allWidth, 80));
        pane.add(JLabelMusic);
        JLabelOut = new JLabel("输出路径：");
        JLabelOut.setFont(new Font("宋体", Font.BOLD, 18));
        JLabelOut.setPreferredSize(new Dimension(allWidth, 80));
        pane.add(JLabelOut);
        String strMsg= "<html><body>备注：<br>1、步骤顺序上传视频;<br>2、上传音频文件;<br>3、选着输出文件夹;<br>4、开始合并生成文件;<br>注意：上传视频文件数量必须和上传音频文件数量相同<body></html>";
        JLabelRemark = new JLabel(strMsg);
        JLabelRemark.setFont(new Font("宋体", Font.BOLD, 20));
        JLabelRemark.setPreferredSize(new Dimension(allWidth, 200));
        JLabelRemark.setForeground(Color.red);//设置文字的颜色
        pane.add(JLabelRemark);
    }
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("视频合成工具");
        frame.setBounds(400, 200, allWidth, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SwingDemo demo=new SwingDemo();
        demo.addComponentsToPane(frame.getContentPane());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        ffmegPath=System.getProperty("user.dir");
        ffmegPath=ffmegPath+"\\ffmpeg\\bin\\ffmpeg.exe";
        System.out.println("ffmegPath:"+ffmegPath);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     *
     * @param mp4InputPath 合成视频路径
     * @param  mp3InputPath 合成音频路径
     * @param videoOutputDir 数据文件夹
     * @throws Exception
     */
    public void createMp4(String mp4InputPath,String mp3InputPath, String videoOutputDir) throws Exception{
        int joinNumber=120;
        File mp3File=new File(mp3InputPath);
        //mp3文件名
        String mp3Name= FileUtil.getName(mp3InputPath);
        //mp3文件夹路径
        String mp3Dir=FileUtil.getParent(mp3InputPath,1);
        StringBuffer lines=new StringBuffer();
        //windwos \r\n linux:\r Mac:\n
        for(int i=0;i<=joinNumber;i++){
            lines.append("file '"+mp3Name+"'\r\n");
        }
        //临时文件路径
        String tempFilePath=mp3Dir+"\\temp.txt";
        //生成临时文件
        File tempFile=new File(tempFilePath);
        if (!tempFile.exists()) {
            tempFile.createNewFile();
        }
        //写入txt文件
        FileUtil.writeString(lines.toString(),tempFilePath,"UTF-8");
        //临时音频文件路径
        String tempWav=mp3Dir+"\\temp.wav";
        //生成时间较长的wav
        convertorCopy(tempFilePath,tempWav);
        //混淆音视频
        convertor(mp4InputPath,tempWav,videoOutputDir);
        //删除临时文件
        FileUtil.del(tempFilePath);
        FileUtil.del(tempWav);
    }
    /**
     *
     * @param mp3InputTxt 音频txt文件
     * @param videoOutputPath 输出音频路径
     * @throws Exception
     */
    public void convertorCopy(String mp3InputTxt, String videoOutputPath) throws Exception {
        java.util.List<String> command2 = new ArrayList<>();
        command2.add(ffmpegEXE);
        command2.add("-safe"); // 支持中文
        command2.add("0");
        command2.add("-y"); // 当已存在输出文件时，不提示是否覆盖
        command2.add("-f");
        command2.add("concat");
        command2.add("-i");
        command2.add(mp3InputTxt);
        command2.add("-c");
        command2.add("copy");
        //采样率
        command2.add("-ar");
        command2.add(String.valueOf(44100));
        //比特率
        command2.add("-ab");
        command2.add("210k");
        //单双声道
        command2.add("-ac");
        command2.add(String.valueOf(2));
        command2.add(videoOutputPath);
        System.out.println();
        for (String c : command2) {
            System.out.print(c + " ");
        }
        this.processed(command2);
    }
    /**
     *
     * @param videoInputPath 视频路径
     * @param mp3InputPath 音频路径
     * @param videoOutputDir 输出路径夹
     * @throws Exception
     */
    public void convertor(String videoInputPath, String mp3InputPath, String videoOutputDir) throws Exception {
        File file=new File(videoInputPath);
        java.util.List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);
        command.add("-i");//输入
        command.add(videoInputPath);
        command.add("-i");
        command.add(mp3InputPath);
        command.add("-c:v");
        command.add("copy");
        command.add("-c:a");//音频文件转aac
        command.add("aac");
        command.add("-strict");//压制
        command.add("experimental");
        command.add("-map");//拷贝
        command.add("0:v:0");
        command.add("-map");
        command.add("1:a:0");
        command.add("-filter_complex");
        command.add("amix=inputs=2:duration=first:");

        //采样率
        command.add("-ar");
        command.add(String.valueOf(44100));
        //比特率
        command.add("-ab");
        command.add("210k");
        //单双声道
        command.add("-ac");
        command.add(String.valueOf(2));

        command.add("-y"); // 当已存在输出文件时，不提示是否覆盖
        command.add(videoOutputDir+"\\"+file.getName());
        System.out.println();

        this.processed(command);
    }

    /**
     * 执行FFmpeg命令
     * @param command
     * @throws IOException
     */
    private void processed(List<String> command){
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process;
        InputStream errorStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        try {
            process = builder.start();
            errorStream = process.getErrorStream();
            inputStreamReader = new InputStreamReader(errorStream);
            br = new BufferedReader(inputStreamReader);

            String line = "";
            while ( (line = br.readLine()) != null ) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (errorStream != null) {
                    errorStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
