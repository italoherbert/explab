package italo.explab_ide.logica.format;

import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.msg.Erro;

public class CodigoFonteFormatResult {
    
    private String codigoFonte;
    private Erro erro;
    
    public CodigoFonteFormatResult( String codigoFonte ) {
        this.codigoFonte = codigoFonte;
    }

    public CodigoFonteFormatResult( Erro erro ) {
        this.erro = erro;
    }

    public CodigoFonteFormatResult( AnaliseResult aresult ) {
        this.erro = aresult.getErro();
    }

    public String getCodigoFonte() {
        return codigoFonte;
    }

    public Erro getErro() {
        return erro;
    }
    
}
