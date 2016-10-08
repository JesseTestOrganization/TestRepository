package com.edianjucai.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.edianjucai.common.Constant;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class ExportPDFUtil {

    public static void exportPDF(HttpServletResponse response, String tempType, String fileName, Map<String, String> data) throws IOException, DocumentException {
        response.setContentType("application/application/vnd.ms-excel");
        response.setHeader("Content-disposition",
                "attachment;filename=" + URLEncoder.encode(fileName + ".pdf", "UTF-8"));
        String tempName = getTempName(tempType); // pdf模板  
        PdfReader reader = new PdfReader(tempName);  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        /* 将要生成的目标PDF文件名称 */   
        PdfStamper ps = new PdfStamper(reader, bos);  
        PdfContentByte under = ps.getUnderContent(1);     
          
          /* 使用中文字体 */    
        BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();  
        fontList.add(bf);  
          
        /* 取出报表模板中的所有字段 */    
        AcroFields fields = ps.getAcroFields();  
        fields.setSubstitutionFonts(fontList);  
        fillData(fields, data);  
          
        /* 必须要调用这个，否则文档不会生成的 */    
        ps.setFormFlattening(true);  
        ps.close();  
          
          
        OutputStream fos = response.getOutputStream();  
        fos.write(bos.toByteArray());  
        fos.flush();  
        fos.close();  
        bos.close();  
    }
    
    public static Map<String, String> data() {  
        Map<String, String> dataMap = new HashMap<String, String>();  
        /*dataMap.put("number", "JK20160908001");  
        dataMap.put("b_idno", "360302199302093014"); 
        dataMap.put("l_name", "袁毅强");
        dataMap.put("l_idno", "306301199508093025");  
        dataMap.put("money", "1300");  
        dataMap.put("capital_money", "壹仟叁佰圆");
        dataMap.put("b_time", "2016年09月08日");
        dataMap.put("term", "6");
        dataMap.put("rate", "0%");
        dataMap.put("l_account_name", "袁毅强");  
        dataMap.put("l_account_no", "60320125468313");  
        dataMap.put("l_account_bank", "招行银行深圳"); */ 
        
        dataMap.put("l_account_name", "袁毅强");  
        dataMap.put("l_account_no", "60320125468313");  
        dataMap.put("l_account_bank", "招行银行深圳");
        dataMap.put("b_time", "2016年09月08日");
        dataMap.put("b_name", "test");
        dataMap.put("money", "1300");
        dataMap.put("m_nomey", 1300.0 / 6 + "");
        dataMap.put("number", "JK20160908001");  
        dataMap.put("b_idno", "360302199302093014");
        dataMap.put("rate", "0%");
        return dataMap;  
    }  
    
    private static String getTempName(String tempType) {
        String tempName = "";
        String path = ExportPDFUtil.class.getResource("/").getPath();
        path = path.substring(0, path.indexOf("WEB-INF") + ("WEB-INF".length() + 1)) + "PDFTemp/";
        switch (tempType) {
        case Constant.REPAYMENT_SCHEDULE:
            tempName = path + "hkTemp.pdf";
            break;
        case Constant.IOU:
            tempName = path + "zjTemp.pdf";
        default:
            break;
        }
        return tempName;
    }

    private static void fillData(AcroFields fields, Map<String, String> data)  
            throws IOException, DocumentException {  
        for (String key : data.keySet()) {  
            String value = data.get(key);  
            fields.setField(key, value); // 为字段赋值,注意字段名称是区分大小写的  
        }  
    }
}
