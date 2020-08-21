package italo.explab.inter.estruturas.condicional;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.inter.InterManager;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.estruturas.node.CompareCaso;
import italo.explab.arvore.estruturas.node.CompareCasoGrupo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.grupo.Grupo;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.estruturas.ComandoInter;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;
import java.util.ArrayList;
import java.util.List;

public class CompareCasoInter extends ComandoInter {
    
    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {                
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();
                
        AnaliseResult aresult = asManager.getCasoAnalisador().analisa( codigo, i );
        if ( aresult.getJ() == 0 )
            return new InterResult( aresult );      
                       
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();
                
        int j = 0;
                
        j += contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.COMPARE );
        j += contUtil.contaEsps( codigo, i+j );
        
        CompareCaso compareCaso = aplic.getExecutor().getExecManager().getExecNoFactory().getEstruturaFactory().novoCompareCaso( i, no, codigo );
        
        boolean esperaFechaParenteses = false;
        if ( codigo.getSEGCH( i+j ) == '(' ) {
            j++;
            j += contUtil.contaEsps( codigo, i+j );
            esperaFechaParenteses = true;
        }
        
        InterResult result = manager.getValorInter().interpreta( compareCaso, base, aplic, codigo, i+j, i2 );
        if ( result.getJ() == 0 )
            return result;

        compareCaso.setExp( (Exp)result.getInstrucaoOuExp() ); 
        
        j += result.getJ();
        j += contUtil.contaEsps( codigo, i+j );
        
        if ( esperaFechaParenteses ) {
            if ( codigo.getSEGCH( i+j ) == ')' ) {
                j++;
                j += contUtil.contaEsps( codigo, i+j );
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
                return new InterResult( erro );
            }
        }
                
        char ch = codigo.getSEGCH( i+j );
        if ( ch != '{' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ABRE_CHAVES_ESPERADO );
            return new InterResult( erro );
        }
                        
        j++;
        j += contUtil.contaEsps( codigo, i+j );
                
        ch = codigo.getSEGCH( i+j );
        if( ch == '}' )
            return new InterResult( j+1 ); 
                
        List<CompareCasoGrupo> cases = new ArrayList();
        CompareCasoGrupo grupoPadrao = null;
        
        boolean fim = false;
        while ( !fim && i+j < i2 ) {
            int cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.PADRAO );
            if ( cont > 0 ) {                
                CompareCasoGrupo grupo = aplic.getExecutor().getExecManager().getExecNoFactory().getEstruturaFactory().novoCompareCasoGrupo( i, compareCaso, codigo );
                
                j += cont;
                j += contUtil.contaEsps( codigo, i+j );                                
                
                ch = codigo.getSEGCH( i+j );
                if ( ch != ':' ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.DOIS_PONTOS_ESPERADO );
                    return new InterResult( erro );
                }
                j++;
                j += contUtil.contaEsps( codigo, i+j );
                
                result = this.interpretaCaseInstrucoes( grupo, aplic, codigo, i+j, i2 );
                if ( result.getJ() == 0 )
                    return result;

                grupoPadrao = grupo; 
                
                j += result.getJ();
                
                ch = codigo.getSEGCH( i+j );
                if ( ch == '}' ) {
                    j++;
                    
                    fim = true;
                    continue;
                }                
            }

            j += contUtil.contaEsps( codigo, i+j );
            cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.CASO );
            if ( cont == 0 ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PALAVRA_RESERVADA_ESPERADA, PalavrasReservadas.CASO );
                return new InterResult( erro );
            }
            j += cont;
            j += contUtil.contaEsps( codigo, i+j );
            
            CompareCasoGrupo grupo = aplic.getExecutor().getExecManager().getExecNoFactory().getEstruturaFactory().novoCompareCasoGrupo( i, compareCaso, codigo );
            
            result = manager.getValorInter().interpreta( grupo, base, aplic, codigo, i+j, i2 );
            if ( result.getJ() == 0 )
                return result;     
            
            grupo.setValor( (Exp)result.getInstrucaoOuExp() ); 

            j += result.getJ();                        
            j += contUtil.contaEsps( codigo, i+j );
                                    
            ch = codigo.getSEGCH( i+j );
            if ( ch != ':' ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.DOIS_PONTOS_ESPERADO );
                return new InterResult( erro );
            }
            j++;
            j += contUtil.contaEsps( codigo, i+j );
            
            result = this.interpretaCaseInstrucoes( grupo, aplic, codigo, i+j, i2 );
            
            cases.add( grupo );
            
            j += result.getJ();            
            j += contUtil.contaEsps( codigo, i+j );
                        
            ch = codigo.getSEGCH( i+j );
            if ( ch == '}' ) {
                j++;                    
                fim = true;                
            }
        }  
        
        if ( fim ) {
            CompareCasoGrupo[] grupos = new CompareCasoGrupo[ cases.size() ];
            grupos = cases.toArray( grupos );
            
            compareCaso.setCasoGrupos( grupos );
            compareCaso.setPadraoGrupo( grupoPadrao ); 
            
            return new InterResult( compareCaso, j );
        }
        
        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i2, ErroMSGs.FECHA_CHAVES_ESPERADO );
        return new InterResult( erro );
    }        
       
    private InterResult interpretaCaseInstrucoes( Grupo grupo, InterAplic aplic, Codigo codigo, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();        
        
        int j = 0;
        
        List<Instrucao> lista = new ArrayList();
        
        boolean interpretou = false;
        do {
            j += contUtil.contaEspsEPontoEVirgulas( codigo, i+j );
            
            char ch = codigo.getSEGCH( i+j );
            if ( ch == '}' ) {    
                interpretou = true;            
                continue;
            } else {
                int cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.CASO );
                if ( cont > 0 ) {
                    interpretou = true;
                    continue;
                }
                cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.PADRAO );
                if ( cont > 0 ) {
                    interpretou = true;
                    continue;
                }
            }
            
            InterResult result = manager.getInstrucaoInter().interpreta( grupo, grupo, aplic, codigo, i+j, i2 );
            if ( result.getJ() == 0 ) {
                return result;
            } else {
                j += result.getJ(); 
        
                if ( result.getInstrucaoOuExp() != null )
                    lista.add( result.getInstrucaoOuExp() );                                
            }
        } while ( !interpretou && i+j < i2 );

        if ( !interpretou ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_CHAVES_ESPERADO ); 
            return new InterResult( erro );
        }        

        Instrucao[] instrucoes = new Instrucao[ lista.size() ];
        instrucoes = lista.toArray( instrucoes );
                
        grupo.getBloco().setInstrucoes( instrucoes ); 
        
        return new InterResult( j );             
    }
    
}
