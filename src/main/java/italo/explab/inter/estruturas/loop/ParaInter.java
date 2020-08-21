package italo.explab.inter.estruturas.loop;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.inter.InterManager;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.estruturas.node.Para;
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

public class ParaInter extends ComandoInter {
            
    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {        
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();
        
        AnaliseResult aresult = asManager.getParaAnalisador().analisa( codigo, i );
        if ( aresult.getJ() == 0 )
            return new InterResult( aresult );
        
        ContadorUtil contUtil = aplic.getContUtil();
                                     
        int j = 0;
        j += contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.PARA );        
        j += contUtil.contaEsps( codigo, i+j );
        
        Para para = aplic.getExecutor().getExecManager().getExecNoFactory().getEstruturaFactory().novoPara( i, no, codigo );        
                
        InterResult result = this.InterpretaInis( para.getIniInstsGrupo(), aplic, codigo, i+j, i2 );        
        if ( result.getJ() == 0 )
            return result;
                                        
        j += result.getJ();
        j += contUtil.contaEsps( codigo, i+j );
            
        InterResult condicaoResult = super.interpretaCondicao( para, para, aplic, codigo, i+j, i2 );
        if ( condicaoResult.getJ() == 0 )
            return condicaoResult;

        para.setCondicao( (Exp)condicaoResult.getInstrucaoOuExp() ); 
        
        j += condicaoResult.getJ();
        j += contUtil.contaEsps( codigo, i+j );
        
        char ch = codigo.getSEGCH( i+j );                
        
        if ( ch != ';' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PONTO_E_VIRGULA_ESPERADO );
            return new InterResult( erro );
        }
        
        j++;
        j += contUtil.contaEsps( codigo, i+j );
         
        result = this.interpretaIncs( para.getAposFimITInstsGrupo(), aplic, codigo, i+j, i2 );
        if ( result.getJ() == 0 )
            return result;
        
        j += result.getJ();              
        j += contUtil.contaEsps( codigo, i+j );
        
        result = super.interpretaBloco( para, para, aplic, codigo, i+j, i2 );
        if ( result.getJ() == 0 )
            return result;
        
        j += result.getJ();
                   
        return new InterResult( para, j );
    }       
    
    public InterResult interpretaIncs( Grupo grupo, InterAplic aplic, Codigo codigo, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();
        
        int j = 0;
        
        j += contUtil.contaEsps( codigo, i+j );
        if ( codigo.getSEGCH( i+j ) == ')' )
            return new InterResult( j+1 );        
        
        List<Instrucao> lista = new ArrayList();
        
        boolean fim = false;        
        while( !fim && i+j < i2 ) {                
            j += contUtil.contaEsps( codigo, i+j );

            InterResult result = manager.getIncDecInter().interpreta( grupo, grupo, aplic, codigo, i+j, i2 );
            if ( result.getJ() == 0 )
                result = manager.getAtribVarInter().interpreta( grupo, grupo, aplic, codigo, i+j, i2 );                                                                   
                        
            if ( result.getJ() == 0 ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.INC_OU_ATRIB_INST_ESPERADA );
                return new InterResult( erro ); 
            }
                        
            manager.getInstrucaoInter().setIncDecsAposCopia( result.getInstrucaoOuExp(), aplic );            
            result.getInstrucaoOuExp().setFinalizadaComPontoEVirgula( true ); 
            
            lista.add( result.getInstrucaoOuExp() );            
            
            j += result.getJ();
            
            j += contUtil.contaEsps( codigo, i+j );

            char ch = codigo.getSEGCH( i+j );
            if ( ch == ',' || ch == ')' ) {
                if ( ch == ')' )
                    fim = true;
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_OU_VIRGULA_ESPERADO );
                return new InterResult( erro );
            }
            
            j++;
        }
        
        if ( fim ) {
            Instrucao[] atribOuIncs = new Instrucao[ lista.size() ];
            atribOuIncs = lista.toArray( atribOuIncs );
            
            grupo.getBloco().setInstrucoes( atribOuIncs );
        } else {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
            return new InterResult( erro );
        }
        
        return new InterResult( j );
    }
            
    public InterResult InterpretaInis( Grupo grupo, InterAplic aplic, Codigo codigo, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();
        
        int j = 0;
            
        if ( codigo.getSEGCH( i+j ) != '(' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ABRE_PARENTESES_ESPERADO );
            return new InterResult( erro );
        }
        
        j++;
        j += contUtil.contaEsps( codigo, i+j );
        
        if ( codigo.getSEGCH( i+j ) == ';' ) 
            return new InterResult( j+1 );
        
        List<Instrucao> lista = new ArrayList();

        boolean fim = false;
        while( !fim && i+j < i2 ) {       
            j += contUtil.contaEsps( codigo, i+j );
                
            InterResult result = manager.getAtribVarInter().interpreta( grupo, grupo, aplic, codigo, i+j, i2 );
            if ( result.getJ() == 0 )
                return result;
                        
            result.getInstrucaoOuExp().setFinalizadaComPontoEVirgula( true ); 
            lista.add( result.getInstrucaoOuExp() );
            
            j += result.getJ();
            j += contUtil.contaEsps( codigo, i+j );

            char ch = codigo.getSEGCH( i+j );
            if ( ch == ',' || ch == ';' ) {
                if ( ch == ';' )
                    fim = true;
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.VIRGULA_OU_PONTO_E_VIRGULA_ESPERADO ); 
                return new InterResult( erro );
            }
            j++;
        }

        if ( fim ) {
            Instrucao[] atribs = new Instrucao[ lista.size() ];
            atribs = lista.toArray( atribs );
            
            grupo.getBloco().setInstrucoes( atribs );             
        } else {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PONTO_E_VIRGULA_ESPERADO );
            return new InterResult( erro );
        }
                    
        return new InterResult( j );
    }   
         
}
