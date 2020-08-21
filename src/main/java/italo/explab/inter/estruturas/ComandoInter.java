package italo.explab.inter.estruturas;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;
import java.util.ArrayList;
import java.util.List;

public abstract class ComandoInter extends Inter {
        
    protected InterResult interpretaBloco( BlocoNo bno, ExecNo base, InterAplic aplic, Codigo codigo, int i, int i2 ) {        
        int j = 0;
        
        char ch = codigo.getSEGCH( i+j );
        if ( ch == '{' ) {
            InterResult instsResult = this.interpretaInstrucoes( bno, base, aplic, codigo, i+j, i2 );
            if ( instsResult.getJ() == 0 )
                return instsResult;
            
            j += instsResult.getJ();
        } else {                     
            InterResult instResult = this.interpretaInstrucao( bno, base, aplic, codigo, i+j, i2 );            
            if ( instResult.getJ() == 0 )            
                return instResult;
                            
            j += instResult.getJ();            
        }   
        
        return new InterResult( j );
    }
            
    protected InterResult interpretaCondicao( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();
        
        int j = 0;
        j += contUtil.contaEsps( codigo, i+j );        

        InterResult result = manager.getBoolExpInter().interpreta( no, base, aplic, codigo, i+j, i2 );
        if ( result.getJ() == 0 ) {
            if ( result.getErro() != null )
                return result;
            
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.BOOLEXP_ESPERADA );                
            return new InterResult( erro ); 
        }
        
        j += result.getJ();

        return new InterResult( result.getInstrucaoOuExp(), j );        
    }
    
    private InterResult interpretaInstrucoes( BlocoNo bno, ExecNo base, InterAplic aplic, Codigo codigo, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();
                
        int j = 0;      
        char ch = codigo.getSEGCH(i+j );
        if ( ch != '{' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ABRE_CHAVES_ESPERADO ); 
            return new InterResult( erro );
        }
            
        j++;
                   
        List<Instrucao> lista = new ArrayList();
        
        boolean interpretou = false;
        do {
            j += contUtil.contaEsps(codigo, i+j );
            
            ch = codigo.getSEGCH( i+j );
            if ( ch == '}' ) {    
                j++;
                interpretou = true;            
                continue;
            }
            
            InterResult result = manager.getInstrucaoInter().interpreta( bno, base, aplic, codigo, i+j, i2 );
            if ( result.getJ() == 0 ) {
                return result;
            } else {
                j += result.getJ(); 
        
                if ( result.getInstrucaoOuExp() != null )
                    lista.add( result.getInstrucaoOuExp() );
                
                j += contUtil.contaEsps( codigo, i+j );

                ch = codigo.getSEGCH(i+j );
                if ( ch == '}' ) {    
                    j++;
                    interpretou = true;            
                }
            }
        } while ( !interpretou && i+j < i2 && !aplic.getSessaoManager().isFim() );

        if ( !interpretou ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_CHAVES_ESPERADO ); 
            return new InterResult( erro );
        }        

        Instrucao[] instrucoes = new Instrucao[ lista.size() ];
        instrucoes = lista.toArray( instrucoes );
        
        bno.getBloco().setInstrucoes( instrucoes ); 
        
        return new InterResult( j );         
    }
    
    private InterResult interpretaInstrucao( BlocoNo bno, ExecNo base, InterAplic aplic, Codigo codigo, int i, int i2 ) {
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();                    
        InterManager manager = aplic.getInterManager();
        
        AnaliseResult instAResult = asManager.getInstrucaoAnalisador().analisa( codigo, i );
        if ( instAResult.getJ() == 0 )
            return new InterResult( instAResult );
        
        if ( codigo.getSEGCH( i ) == ';' )
            return new InterResult( 1 );

        InterResult ir = manager.getInstrucaoInter().interpreta( bno, base, aplic, codigo, i, i2 );                        
        if ( ir.getInstrucaoOuExp() != null )
            bno.getBloco().setInstrucoes( new Instrucao[] { ir.getInstrucaoOuExp() } );

        return ir;        
    }
    
}
