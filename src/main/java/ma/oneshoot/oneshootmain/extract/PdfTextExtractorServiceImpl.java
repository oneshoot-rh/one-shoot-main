package ma.oneshoot.oneshootmain.extract;


import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class PdfTextExtractorServiceImpl {

    List<String> headers = new ArrayList<>();

    {
        headers.add("Contact");
        headers.add("Top Skills");
        headers.add("Languages");
        headers.add("Certifications");
        headers.add("Summary");
        headers.add("Experience");
        headers.add("Education");
    }

    public  Map<String,String> extractTextFromPdf(String pdfPath) throws FileNotFoundException {
        File file = new File(pdfPath);
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper textStripper = new PDFTextStripper() {
                private boolean isHeader = false;

                @Override
                protected void startPage(PDPage pageNo) throws IOException {
                    super.startPage(pageNo);
                    isHeader = false;
                }

                @Override
                protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
                    if (isHeader(textPositions)) {
                        writeLineSeparator();
                        isHeader = true;
                    }
                    super.writeString(string, textPositions);
                }

                private boolean isHeader(List<TextPosition> textPositions) {
                    return textPositions.stream().anyMatch(position -> position.getFontSize() > 16);
                }
            };

            textStripper.setStartPage(1);
            textStripper.setEndPage(document.getNumberOfPages());
            textStripper.setAddMoreFormatting(true);

            String text = textStripper.getText(document);
            return categorizeText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Map<String,String> categorizeText(String text){
        Map<String,String> categorizedText = new HashMap<>();
        for (int i = headers.size()-1 ; i>= 0; i--){
            String header = "(?<=\\r\\n)"+headers.get(i)+"(?=\\r\\n)";
            Pattern pattern = Pattern.compile(header);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()){
                String target = matcher.group();
                String[] splittedText = text.split(Pattern.quote(target));
                String bodyText = splittedText[1];
                // remove content extracted
                text = text.replace(target, "");// to avoid removing the same word if repeated in the body
                text = text.replace(bodyText, "");
                categorizedText.put(target, bodyText);
            }
        }
        return categorizedText;
    }


}
