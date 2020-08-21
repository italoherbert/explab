package italo.explab.func.plot.build;

import italo.explab.func.plot.obj.ExpLabJanelaConfig;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.classe.util.ClasseUtil;
import italo.explab.recursos.classe.util.ClasseUtilException;
import italo.explab.recursos.classe.util.VariavelNome;
import italo.explab.var.StringVar;
import italo.explab.var.num.RealVar;

public class JanelaConfigBuilder {
    
    public ExpLabJanelaConfig build( RecursoManager pcObjRecursos, VariavelNome pcObjVN, ClasseUtil classeUtil ) throws ClasseUtilException {
        ExpLabJanelaConfig janela = new ExpLabJanelaConfig();
        
        VariavelNome janelaVN = new VariavelNome( "janela", pcObjVN );

        Objeto janelaObj = classeUtil.buscaObjeto( pcObjRecursos, "JanelaConfig", janelaVN );
        if ( janelaObj != null ) {
            RecursoManager janelaObjRecursos = janelaObj.getRecursos();
            StringVar tituloVar = classeUtil.stringValor( janelaObjRecursos, new VariavelNome( "titulo", janelaVN ) );
            RealVar larguraVar = classeUtil.realValor( janelaObjRecursos, new VariavelNome( "largura", janelaVN ) );
            RealVar alturaVar = classeUtil.realValor( janelaObjRecursos, new VariavelNome( "altura", janelaVN ) );

            if ( tituloVar != null )
                janela.setTitulo( tituloVar.getValor() ); 
            if ( larguraVar != null )
                janela.setLargura( (int)larguraVar.getValor() ); 
            if ( alturaVar != null )
                janela.setAltura( (int)alturaVar.getValor() ); 
        }
        
        return janela;
    }
    
}
