package italo.explab;

import java.io.InputStream;
import java.text.DecimalFormat;

public class InterIni implements InterStream {
    
    private double[] vetorX = null;
    private InterStream errStream = this;
    private InterStream outStream = this;
    private InputStream errArqINStream = null; 
    private InputStream infoArqINStream = null;
    private DecimalFormat decimalFormat = new DecimalFormat( "0.####" );
    private String arqCharset = "UTF-8";

    @Override
    public void envia( String texto ) {
        System.out.print( texto );
    }
    
    public double[] getVetorX() {
        return vetorX;
    }
    
    public void setVetorX( double[] vetorX ) {
        this.vetorX = vetorX;
    }
    
    public InterStream getErrStream() {
        return errStream;
    }

    public void setErrStream(InterStream errStream) {
        this.errStream = errStream;
    }

    public InterStream getOutStream() {
        return outStream;
    }

    public void setOutStream(InterStream outStream) {
        this.outStream = outStream;
    }

    public InputStream getErrArqINStream() {
        return errArqINStream;
    }

    public void setErrArqINStream(InputStream errArqINStream) {
        this.errArqINStream = errArqINStream;
    }

    public InputStream getInfoArqINStream() {
        return infoArqINStream;
    }

    public void setInfoArqINStream(InputStream infoArqINStream) {
        this.infoArqINStream = infoArqINStream;
    }

    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    public void setDecimalFormat(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    public String getArqCharset() {
        return arqCharset;
    }

    public void setArqCharset(String arqCharset) {
        this.arqCharset = arqCharset;
    }
    
}
