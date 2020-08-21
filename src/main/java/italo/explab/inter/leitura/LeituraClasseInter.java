package italo.explab.inter.leitura;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.atrib.Atrib;
import italo.explab.codigo.Codigo;
import italo.explab.construtor.Construtor;
import italo.explab.func.UsuarioFunc;
import italo.explab.inter.Inter;
import italo.explab.inter.InterTO;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.leitura.result.LeituraClasseInterResult;
import italo.explab.inter.leitura.result.LeituraConstrutorInterResult;
import italo.explab.inter.leitura.result.LeituraFuncInterResult;
import italo.explab.inter.leitura.to.LeituraClasseInterVO;
import italo.explab.msg.CodigoErro;
import italo.explab.recursos.ClasseManager;
import italo.explab.recursos.classe.Classe;
import italo.explab.recursos.construtor.ConstrutorLista;
import italo.explab.util.ContadorUtil;
import java.util.ArrayList;
import java.util.List;

public class LeituraClasseInter extends Inter {

    @Override
    public LeituraClasseInterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        ContadorUtil contUtil = aplic.getContUtil();

        int cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.CLASSE );
        if ( cont == 0 )
            return new LeituraClasseInterResult();        
        
        InterManager manager = aplic.getInterManager();
        PalavrasReservadas prs = aplic.getPalavrasReservadas();
                        
        ClasseManager classeManager = aplic.getClasseManager();
                
        String pacote = null;
        if ( to != null )
            pacote = ((LeituraClasseInterVO)to).getPacote();
                        
        String classeNome;
        Classe classe;
        Classe superClasse = null;
                
        int j = cont;
        j += contUtil.contaEsps( codigo, i+j );
                
        cont = contUtil.contaClasseOuPacoteNomeTam( codigo, i+j );
        if ( cont == 0 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CLASSE_NOME_ESPERADO );
            return new LeituraClasseInterResult( erro ); 
        }
                        
        classeNome = codigo.getCodigo().substring( i+j, i+j+cont );                     
        boolean ehPR = prs.verificaSePalavraReservada( classeNome );
        if ( ehPR ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.EXISTE_COMO_PALAVRA_RESERVADA , classeNome );
            return new LeituraClasseInterResult( erro );
        }             
        
        j += cont;
        j += contUtil.contaEsps( codigo, i+j );
        
        cont = contUtil.contaTextoValor(codigo, i+j, PalavrasReservadas.EXTENDE );           
        if ( cont > 0 ) {
            j += cont;
            j += contUtil.contaEsps( codigo, i+j );
            
            cont = contUtil.contaClasseOuPacoteNomeTam( codigo, i+j );
            if ( cont == 0 ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CLASSE_NOME_ESPERADO );
                return new LeituraClasseInterResult( erro ); 
            }
            
            String superClasseNome = codigo.getCodigo().substring( i+j, i+j+cont );
            superClasse = classeManager.buscaClasse( superClasseNome );                                    
                        
            j += cont;                                                                   
            j += contUtil.contaEsps( codigo, i+j ); 
        }
        
        classe = classeManager.novaClasse( pacote, classeNome, codigo );                                             
        classe.setSuperClasse( superClasse );         
        
        char ch = codigo.getSEGCH( i+j );
        if ( ch != '{' ) {                
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ABRE_CHAVES_ESPERADO ); 
            return new LeituraClasseInterResult( erro );
        }
        
        j++;
        j += contUtil.contaEsps( codigo, i+j );
        
        List<Atrib> classeAtribuicoes = new ArrayList();
        
        boolean fim = false;
        while( !fim && i+j < i2 ) {                    
            j += contUtil.contaEsps( codigo, i+j );
            
            int k = j;
            
            ch = codigo.getSEGCH( i+j );
            if ( ch == '}' ) {
                fim = true;
                continue;
            }            
                        
            cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.FUNC );
            if ( cont > 0 ) {
                LeituraFuncInterResult funcResult = (LeituraFuncInterResult)manager.getLeituraFuncInter().interpreta( no, base, aplic, codigo, i+j, i2 );                    
                if ( funcResult.getJ() == 0 )
                    return new LeituraClasseInterResult( funcResult );                                                    
                                
                UsuarioFunc func = funcResult.getFunc();                
                func.setClasse( classe ); 
                
                classe.getClasseRecursoManager().getFuncLista().criaOuAltera( func );                
                                      
                cont = funcResult.getJ();
            } else {
                cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.CONSTRUTOR );
                if ( cont > 0 ) {
                    LeituraConstrutorInterResult construtorResult = (LeituraConstrutorInterResult)manager.getLeituraConstrutorInter().interpreta( no, base, aplic, codigo, i+j, i2 );                    
                    if ( construtorResult.getJ() == 0 )
                        return new LeituraClasseInterResult( construtorResult );                                        
                
                    Construtor construtor = construtorResult.getConstrutor();
                    ConstrutorLista construtorLista = classe.getClasseRecursoManager().getConstrutorLista();
                    boolean ok = construtorLista.addConstrutor( construtor );
                    if ( ok ) {
                        cont = construtorResult.getJ();                                                                        
                    } else {
                        int psize = construtor.getParametros().length;
                        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CONSTRUTOR_JA_EXISTE, String.valueOf( psize ) );
                        return new LeituraClasseInterResult( erro );
                    }
                } else {
                    cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.CLASSE );
                    if ( cont > 0 ) {
                        LeituraClasseInterResult lclasseResult = this.interpreta( no, base, aplic, codigo, null, i+j, i2 );
                        if ( lclasseResult.getJ() == 0 )
                            return lclasseResult;                        
                        
                        classe.getClasseRecursoManager().getClasseLista().criaOuAltera( lclasseResult.getClasse() );
                        lclasseResult.getClasse().setContainerClasse( classe ); 
                    
                        cont = lclasseResult.getJ();
                    } 
                }
            }
            
            j += cont;

            if ( cont == 0 ) {                                                         
                boolean executouAtributos = false;
                
                boolean achouFimVarsBloco = false;
                while ( !achouFimVarsBloco && i+j < i2 ) { 
                    InterResult ir = manager.getAtribVarInter().interpreta( no, base, aplic, codigo, i+j, i2 );
                    if ( ir.getErro() != null )
                        return new LeituraClasseInterResult( ir );                                                                                                            
                                        
                    if ( ir.getJ() == 0 ) {
                        achouFimVarsBloco = true;
                    } else {
                        executouAtributos = true;
                        classeAtribuicoes.add( (Atrib)ir.getInstrucaoOuExp() );
                    }                    
                    
                    j += ir.getJ();
                    j += contUtil.contaEspsEPontoEVirgulas( codigo, i+j );                    
                }           
                
                if ( !executouAtributos ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+k, ErroMSGs.CLASSE_DEF_ATRIB_INVALIDA );
                    return new LeituraClasseInterResult( erro );
                }
            }                                                
        }        
        
        Atrib[] atribuicoes = new Atrib[ classeAtribuicoes.size() ];
        atribuicoes = classeAtribuicoes.toArray( atribuicoes );
                
        classe.setAtribuicoes( atribuicoes );                
        
        if ( fim )
            return new LeituraClasseInterResult( classe, j+1 );   
        
        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_CHAVES_ESPERADO );
        return new LeituraClasseInterResult( erro );
    }
    
}
