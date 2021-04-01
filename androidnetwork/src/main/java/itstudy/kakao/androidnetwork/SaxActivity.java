package itstudy.kakao.androidnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SaxActivity extends AppCompatActivity {
    TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sax);

        mResult = (TextView)findViewById(R.id.result);
    }

    public void click(View v) {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                + "<root><team>Kia</team><team>Samsung</team></root>";

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            SaxHandler handler = new SaxHandler();
            reader.setContentHandler(handler);
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            reader.parse(new InputSource(istream));
            mResult.setText("팀 명: " + handler.item);
        }
        catch (Exception e) {
            Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    class SaxHandler extends DefaultHandler {
        boolean initem = false;
        StringBuilder item = new StringBuilder();
        public void startDocument () {}
        public void endDocument() {}
        public void startElement(String uri, String localName, String qName, Attributes atts) {
            if (localName.equals("team")) {
                initem = true;
            }
        }
        public void endElement(String uri, String localName, String qName) {}

        public void characters(char[] chars, int start, int length) {
            if (initem) {
                item.append(chars, start, length);
                item.append(":");
                initem = false;
            }
        }
    };

}