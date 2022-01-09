package eapli.base.stock.application;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;

public class ExportarParaXMLController {

    public void exportarClasseParaXML(String encomenda, String linha, String maquina, String ordemProduc, String dataIn,
                                      String dataFim, String categoria, String deposito, String materiaPrima, String produto){


        ListsXML lxml = new ListsXML();
        lxml.criarInstanciaListXML(encomenda, linha, maquina, ordemProduc, dataIn,
                dataFim, categoria, deposito,  materiaPrima, produto);
        converterXML(lxml);
    }


    public void converterXML(ListsXML lxml) {
        try{
            JAXBContext contextObj = JAXBContext.newInstance(ListsXML.class);

            Marshaller marshallerObj = contextObj.createMarshaller();

            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            FileOutputStream outputStream = new FileOutputStream(new File("./instancias.xml"));
            marshallerObj.marshal(lxml, outputStream);

        } catch(JAXBException e){
            e.printStackTrace();
        } catch(FileNotFoundException f){
            f.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
