package itstudy.kakao.androidnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DomActivity extends AppCompatActivity {
    TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dom);

        mResult = (TextView)findViewById(R.id.result);

    }

    /*
    public void click(View v) {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                + "<root><team>Kia</team><team>Samsung</team></root>";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(
                    xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);
            Element root = doc.getDocumentElement();
            NodeList items = root.getElementsByTagName("team");

            StringBuilder sb = new StringBuilder();
            int size = items.getLength();
            for (int i = 0; i < size; i++) {
                Node team = items.item(i);
                Node text = team.getFirstChild();
                String itemName = text.getNodeValue();
                sb.append(itemName + ":");
            }
            mResult.setText("팀명 : " + sb);
        } catch (Exception e) {
            Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

     */

    public void click(View v) {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<baseball>" +
                "<team name=\"Samsung\">류중일</team>" +
                "<team name=\"Nexson\">염경엽</team>" +
                "<team name=\"KIA\">김기태</team>" +
                "</baseball>";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            Element baseball = doc.getDocumentElement();
            NodeList items = baseball.getElementsByTagName("team");
            String Result = "";
            for (int i = 0; i < items.getLength();i++) {
                Node item = items.item(i);
                Node text = item.getFirstChild();
                String directorName = text.getNodeValue();
                Result += directorName + " : ";

                NamedNodeMap Attrs = item.getAttributes();
                for (int j = 0;j < Attrs.getLength(); j++) {
                    Node attr = Attrs.item(j);
                    Result += (attr.getNodeName() + " = " + attr.getNodeValue() + "  ");
                }
                Result += "\n";
            }
            mResult.setText("프로야구\n" + Result);
        }
        catch (Exception e) {
            Toast.makeText(v.getContext(), e.getMessage(), 0).show();
        }
    }


}