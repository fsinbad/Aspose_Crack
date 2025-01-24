package org.example;

import com.aspose.pdf.*;
import com.aspose.pdf.facades.PdfContentEditor;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static java.io.IO.println;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void Init() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("src/main/resources/license.xml");
            License license = new License();
            try {
                license.setLicense(fis);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static void Remove(String filename) {
        Document pdfDocument = new Document(filename);

        // Create TextAbsorber object to find all instances of the input search phrase
        TextFragmentAbsorber textFragmentAbsorber = new TextFragmentAbsorber("趣卡翻译（fanyi.qukaa.com）");

        // Accept the absorber for first page of document
        pdfDocument.getPages().accept(textFragmentAbsorber);

        // Get the extracted text fragments into collection
        TextFragmentCollection textFragmentCollection = textFragmentAbsorber.getTextFragments();

        // Loop through the fragments
        for (TextFragment textFragment : (Iterable<TextFragment>) textFragmentCollection) {
            // Update text and other properties
            textFragment.setText("");
            textFragment.getTextState().setFont(FontRepository.findFont("Verdana"));
            textFragment.getTextState().setFontSize(12);
            textFragment.getTextState().setForegroundColor(Color.getBlue());
            textFragment.getTextState().setBackgroundColor(Color.getGray());
        }
        // Save the updated PDF file
        pdfDocument.save(filename);
    }

    private static void search(File file) {
        System.out.println(file.getName());
        if (!file.exists()) {
            System.out.println("file dose not exist");
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                search(f);
            }
        } else {
            Remove(file.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        String basePath = "D:\\手册汉化";
        File dir = new File(basePath);
        search(dir);

    }
}