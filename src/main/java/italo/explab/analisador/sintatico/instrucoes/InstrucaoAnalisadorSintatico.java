package italo.explab.analisador.sintatico.instrucoes;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import java.util.ArrayList;
import java.util.List;

public class InstrucaoAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;
    
    private final List<AnalisadorSintatico> instsAnalisadores = new ArrayList();

    public InstrucaoAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
        
        instsAnalisadores.add( manager.getCMDsAnalisador() );
        instsAnalisadores.add( manager.getLeituraFuncAnalisador() );
        instsAnalisadores.add( manager.getLeituraClasseAnalisador() );        
        instsAnalisadores.add( manager.getSeAnalisador() );        
        instsAnalisadores.add( manager.getParaAnalisador() );
        instsAnalisadores.add( manager.getEnquantoAnalisador() );
        instsAnalisadores.add( manager.getFacaEnquantoAnalisador() );
        instsAnalisadores.add( manager.getCasoAnalisador() );
        instsAnalisadores.add( manager.getTenteCaptureAnalisador() );
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        if ( !codigo.isCHValido( i ) )
            return new AnaliseResult();
        
        for( AnalisadorSintatico inst : instsAnalisadores ) {
            AnaliseResult result = inst.analisa( codigo, i );
            if ( result.getErro() != null )
                return result;            

            if ( result.getJ() > 0 ) {
                int j = result.getJ();
                j += manager.getContUtil().contaEspsEPontoEVirgulas( codigo, i+j );
                return new AnaliseResult( j ); 
            }
        }            

        AnaliseResult result = manager.getValorAnalisador().analisa( codigo, i );                                        
        if ( result.getErro() != null )
            return result;                    

        int j = result.getJ();

        int k = j + manager.getContUtil().contaEsps( codigo, i+j );
        if ( codigo.getSEGCH( i+k ) == ';' )                
            j = k+1;            

        if ( j > 0 )
            return new AnaliseResult( j );
            
        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.INSTRUCAO_DESCONHECIDA );
        return new AnaliseResult( erro ); 
    }

    public List<AnalisadorSintatico> getInstsAnalisadores() {
        return instsAnalisadores;
    }
    
}
