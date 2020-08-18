package italo.explab.func.plot.build;

import italo.explab.func.plot.obj.ExpLabPontoConfig;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.classe.util.ClasseUtil;
import italo.explab.recursos.classe.util.ClasseUtilException;
import italo.explab.recursos.classe.util.VariavelNome;
import italo.explab.var.BooleanVar;
import italo.explab.var.num.RealVar;
import java.awt.Color;

public class PontoConfigBuilder {
    
    public ExpLabPontoConfig build( RecursoManager dadosObjRecursos, VariavelNome dadosVN, ClasseUtil classeUtil ) throws ClasseUtilException {
        ExpLabPontoConfig pontoConfig = new ExpLabPontoConfig();
        
        VariavelNome pontosConfigVN = new VariavelNome( "ponto", dadosVN );
        Objeto pontoObj = classeUtil.buscaObjeto( dadosObjRecursos, "PontoConfig", pontosConfigVN );
        if ( pontoObj != null ) {
            RecursoManager pontoObjRecursos = pontoObj.getRecursos();
            BooleanVar pontosVisiveis = classeUtil.booleanValor( pontoObjRecursos, new VariavelNome( "visivel", pontosConfigVN ) );
            RealVar pontosRaio = classeUtil.realValor( pontoObjRecursos, new VariavelNome( "raio", pontosConfigVN ) );
            Color pontosCor = classeUtil.corValor( pontoObjRecursos, new VariavelNome( "cor", pontosConfigVN ) );
            
            if ( pontosVisiveis != null )
                pontoConfig.setVisivel( pontosVisiveis.getValor() );
            if ( pontosRaio != null )
                pontoConfig.setRaioPX( (int)pontosRaio.getValor() );
            pontoConfig.setCor( pontosCor );
        }

        return pontoConfig;
    }
    
}

