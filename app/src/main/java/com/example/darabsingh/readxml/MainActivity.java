package com.example.darabsingh.readxml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.darabsingh.readxml.pojo.Employee;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void readXML(View view)
    {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(getAssets().open("employees.xml"),new DefaultHandler(){
                ArrayList<Employee>list;
                Employee employee;
                String msg="";
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    super.startElement(uri, localName, qName, attributes);
                    if(qName.equals("employees"))
                    {
                        list=new ArrayList<>();
                    }
                    if (qName.equals("employee"))
                    {
                        employee=new Employee();
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    super.characters(ch, start, length);
                    msg=new String(ch,start,length);
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    super.endElement(uri, localName, qName);
                    if(qName.equals("id"))
                    {
                        employee.setId(Integer.parseInt(msg));
                    }
                    if (qName.equals("name"))
                    {
                        employee.setName(msg);
                    }
                    if (qName.equals("designation"))
                    {
                        employee.setDesignation(msg);
                    }
                    if (qName.equals("dept"))
                    {
                        employee.setDepartment(msg);
                    }
                    if (qName.equals("employee"))
                    {
                        list.add(employee);
                    }
                    if (qName.equals("employees"))
                    {
                        ArrayList<String>tmp_list=new ArrayList<>();

                    for (Employee employee:list)
                    {
                        tmp_list.add(employee.getId()+" \n " +employee.getName()+" \n " +employee.getDesignation()+" \n "
                        +employee.getDepartment());
                    }
                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                                MainActivity.this,android.R.layout.simple_spinner_item,tmp_list
                        );
                        ListView listView=findViewById(R.id.listView);
                        listView.setAdapter(adapter);
                    }
                }
            });
        }
        catch (Exception e)
        {

        }
    }
}
