package italo.explab.inter.instrucao;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.InterManager;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;
import java.util.ArrayList;
import java.util.List;

public class InstrucaoInter extends Inter {
    
    private final List<Inter> interpretadores = new ArrayList();
    
    public InstrucaoInter( InterManager manager ) {        
        interpretadores.add( manager.getCMDsInter() );        
        interpretadores.add( manager.getEsteChamadaConstrutorInter() );
        interpretadores.add( manager.getSuperChamadaConstrutorInter() );
        
        interpretadores.add( manager.getAtribVarInter() );
        interpretadores.add( manager.getLeituraGlobalFuncInter() );
        interpretadores.add( manager.getLeituraGlobalClasseInter() );
                  
        interpretadores.add( manager.getSeInter() );        
        interpretadores.add( manager.getParaInter() );
        interpretadores.add( manager.getEnquantoInter() );
        interpretadores.add( manager.getFacaEnquantoInter() );
        interpretadores.add( manager.getCompareCasoInter() );
        interpretadores.add( manager.getTenteCaptureInter() );
        
        interpretadores.add( manager.getValorInter() );
    }
    
    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {        
        ContadorUtil contUtil = aplic.getContUtil();

        int instJ = 0;
        try {                     
            for( Inter inter : interpretadores ) {
                InterResult result = inter.interpreta( no, no, aplic, codigo, i, i2 );
                if ( result.getErro() != null )
                    return result;                
                
                if ( result.getJ() > 0 ) {
                    Instrucao inst = result.getInstrucaoOuExp();                     

                    int j = result.getJ();
                    j += contUtil.contaEsps( codigo, i+j );
                                        
                    if ( inst != null ) {
                        this.setIncDecsAposCopia( inst, aplic );
                        inst.setFinalizadaComPontoEVirgula( codigo.getSEGCH( i+j ) == ';' );                                         
                    }

                    j += contUtil.contaEspsEPontoEVirgulas( codigo, i+j );                                        
                    return new InterResult( inst, j ); 
                }
            }                                                                       
                                         
            String palavra = codigo.palavra( i );
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i, ErroMSGs.TERMO_NAO_EXISTE, palavra );
            return new InterResult( erro );                                                                         
        } catch ( StackOverflowError e ) {            
            aplic.getSessaoManager().fim();
            
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+instJ, ErroMSGs.MEM_ESTOURO ); 
            return new InterResult( erro );
        } catch ( Exception e ) {
            e.printStackTrace();

            aplic.getSessaoManager().fim();            

            String msg = "Desconhecida!";
            if ( e.getMessage() != null )
                msg = e.getMessage();
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+instJ, ErroMSGs.JAVA_EXCEPTION, msg ); 
                        
            return new InterResult( erro );
        }        
    }
    
    public void setIncDecsAposCopia( Instrucao inst, InterAplic aplic ) {
        inst.setIncDecsAposCopia( aplic.getSessaoManager().popIncDecs() ); 
    }
    
}
