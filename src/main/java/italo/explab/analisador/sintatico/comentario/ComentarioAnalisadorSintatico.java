package italo.explab.analisador.sintatico.comentario;

import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.util.ContadorUtil;

public class ComentarioAnalisadorSintatico implements AnalisadorSintatico {
    
    private final AnalisadorSintaticoManager manager;

    public ComentarioAnalisadorSintatico(AnalisadorSintaticoManager manager) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        ContadorUtil contUtil = manager.getContUtil();
        if ( contUtil.temComentario( codigo, i ) ) {
            int j = contUtil.contaComentariosTam( codigo, i );
            return new AnaliseResult( j );
        }
        
        return new AnaliseResult();
    }
    
}
