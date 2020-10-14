package italo.explab_ide.logica.format;

import italo.explab.ExpLab;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;
import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.logica.format.el.PalavraReservadaFormat;
import italo.explab_ide.logica.format.el.ClasseLeituraFormat;
import italo.explab_ide.logica.format.el.ComentarioFormat;
import italo.explab_ide.logica.format.el.FuncLeituraFormat;
import italo.explab_ide.logica.format.el.FuncNativaFormat;
import italo.explab_ide.logica.format.el.LeituraFormat;
import italo.explab_ide.logica.format.el.StringFormat;
import italo.explab_ide.logica.format.el.ValorFormat;
import italo.explab_ide.logica.format.el.VarNativaFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.AttributeSet;

public class CodigoFonteManager {
    
    private final ExpLabIDEAplic aplic;
    
    private final ComentarioFormat comentarioFormat;
    private final PalavraReservadaFormat palavraReservadaFormat;
    private final StringFormat stringFormat;
    
    private final ClasseLeituraFormat classeLeituraFormat;
    private final FuncLeituraFormat funcLeituraFormat;
    
    private final VarNativaFormat varNativaFormat;
    private final FuncNativaFormat funcNativaFormat;
    
    private final CodigoFonteFormatConfig cfg = new CodigoFonteFormatConfig();
    
    private final ValorFormat[] valorFormatadores;
    
    public final LeituraFormat[] leituraFormatadores;

    public CodigoFonteManager( ExpLabIDEAplic aplic ) {
        this.aplic = aplic;
    
        this.palavraReservadaFormat = new PalavraReservadaFormat( aplic );
        this.comentarioFormat = new ComentarioFormat( aplic );
        this.stringFormat = new StringFormat( aplic );
        this.varNativaFormat = new VarNativaFormat( aplic );
        this.funcNativaFormat = new FuncNativaFormat( aplic );
        
        this.classeLeituraFormat = new ClasseLeituraFormat( aplic );
        this.funcLeituraFormat = new FuncLeituraFormat( aplic, palavraReservadaFormat );
        
        valorFormatadores = new ValorFormat[] {
            palavraReservadaFormat,
            comentarioFormat,
            stringFormat,
            varNativaFormat,
            funcNativaFormat
        };
        
        leituraFormatadores = new LeituraFormat[] {
            classeLeituraFormat,
            funcLeituraFormat
        };
    }
         
    public void formata( ExpLab explab, CodigoFonteDocumento doc, String codigoFonte ) {                               
        Codigo codigo = new Codigo( explab.getAplic(), codigoFonte );                                        
        List<CodigoErro> erros = new ArrayList( 0 );      
        
        this.formata( doc, erros, codigo, 0, codigo.getCodlen() );                                                                       
    }
    
    
    private int formata( CodigoFonteDocumento doc, List<CodigoErro> erros, Codigo codigo, int i, int i2 ) {
        ContadorUtil contUtil = aplic.getContadorUtil();
        
        AttributeSet normalStyle = cfg.getNormalStyle();
        
        int j = 0;
        boolean fim = false;
        while( !fim && i+j < i2 ) {
            boolean achou = false;
            AnaliseResult result;
            
            int cont = contUtil.contaEspsEPontoEVirgulas( codigo, i+j );
            doc.append( codigo.getCodigo().substring( i+j, i+j+cont ), normalStyle );
            j += cont;                
                        
            for( int k = 0; !achou && k < leituraFormatadores.length; k++ ) {
                result = leituraFormatadores[k].processa( this, doc, erros, codigo, i+j );                                           
                if ( result.getJ() > 0 ) {
                    j += result.getJ();
                    achou = true;         
                }
            }
            
            if ( !achou ) {
                for( int k = 0; !achou && k < valorFormatadores.length; k++ ) {                
                    result = valorFormatadores[k].processa( codigo, i+j );
                    if ( result.getJ() > 0 ) {
                        AttributeSet style = valorFormatadores[k].getStyle( cfg );                        
                        doc.append( codigo.getCodigo().substring( i+j, i+j+result.getJ() ), style );

                        j += result.getJ();
                        achou = true;
                    }                
                }
            }
                 
            if ( !achou ) {
                if ( codigo.isCHValido( i+j ) ) {
                    doc.append( String.valueOf( codigo.getCH( i+j ) ), normalStyle );
                    j++;                    
                } else {
                    fim = true;
                }
            }  
            
        }  
        
        return j;
    }

    public ComentarioFormat getComentarioFormat() {
        return comentarioFormat;
    }

    public PalavraReservadaFormat getPalavraReservadaFormat() {
        return palavraReservadaFormat;
    }

    public StringFormat getStringFormat() {
        return stringFormat;
    }

    public ClasseLeituraFormat getClasseFormat() {
        return classeLeituraFormat;
    }

    public FuncLeituraFormat getFuncLeituraFormat() {
        return funcLeituraFormat;
    }

    public CodigoFonteFormatConfig getFormatConfig() {
        return cfg;
    }

}
