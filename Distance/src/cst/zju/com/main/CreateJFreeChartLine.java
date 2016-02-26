package cst.zju.com.main;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
  
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;  
import org.jfree.chart.JFreeChart;  
import org.jfree.chart.plot.CategoryPlot;  
import org.jfree.chart.plot.PlotOrientation;  
import org.jfree.data.category.CategoryDataset;  
import org.jfree.data.general.DatasetUtilities;  
  
public class CreateJFreeChartLine {     
    
    /**    
     * 创建JFreeChart Line Chart（折线图）    
     */    
    public static void main(String[] args) {     
        //步骤1：创建CategoryDataset对象（准备数据）     
        CategoryDataset dataset = createDataset();     
        //步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置     
        JFreeChart freeChart = createChart(dataset);   
        ChartFrame  frame=new ChartFrame ("折线图 ",freeChart,true);
        frame.pack();
         frame.setVisible(true);
        //步骤3：将JFreeChart对象输出到文件，Servlet输出流等     
     //   saveAsFile(freeChart, "F:\\jfreechart\\line.png", 600, 400);     
    }     
    
    // 保存为文件     
    public static void saveAsFile(JFreeChart chart, String outputPath,     
            int weight, int height) {     
        FileOutputStream out = null;     
        try {     
            File outFile = new File(outputPath);     
            if (!outFile.getParentFile().exists()) {     
                outFile.getParentFile().mkdirs();     
            }     
            out = new FileOutputStream(outputPath);     
            // 保存为PNG     
            ChartUtilities.writeChartAsPNG(out, chart, weight, height);     
            // 保存为JPEG     
            // ChartUtilities.writeChartAsJPEG(out, chart, weight, height);     
            out.flush();     
        } catch (FileNotFoundException e) {     
            e.printStackTrace();     
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {     
            if (out != null) {     
                try {     
                    out.close();     
                } catch (IOException e) {     
                    // do nothing     
                }     
            }     
        }     
    }     
    
    // 根据CategoryDataset创建JFreeChart对象     
    public static JFreeChart createChart(CategoryDataset categoryDataset) {     
        // 创建JFreeChart对象：ChartFactory.createLineChart     
        JFreeChart jfreechart = ChartFactory.createLineChart("Line Chart Demo", // 标题     
                "年分", // categoryAxisLabel （category轴，横轴，X轴标签）     
                "数量", // valueAxisLabel（value轴，纵轴，Y轴的标签）     
                categoryDataset, // dataset     
                PlotOrientation.VERTICAL, true, // legend     
                false, // tooltips     
                false); // URLs     
    
        // 使用CategoryPlot设置各种参数。以下设置可以省略。     
        CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();     
        // 背景色 透明度     
        plot.setBackgroundAlpha(0.5f);     
        // 前景色 透明度     
        plot.setForegroundAlpha(0.5f);     
        // 其他设置 参考 CategoryPlot类     
    
        return jfreechart;     
    }     
    
    /**    
     * 创建CategoryDataset对象    
     *     
     */    
    public static CategoryDataset createDataset() {     
    
        String[] rowKeys = { "One", "Two", "Three" };     
        String[] colKeys = { "1987", "1997", "2007" };     
    
        double[][] data = { { 50, 20, 30 }, { 20, 10D, 40D },     
                { 40, 30.0008D, 38.24D }, };     
    
        // 或者使用类似以下代码     
        // DefaultCategoryDataset categoryDataset = new     
        // DefaultCategoryDataset();     
        // categoryDataset.addValue(10, "rowKey", "colKey");     
    
        return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);     
    }     
}  