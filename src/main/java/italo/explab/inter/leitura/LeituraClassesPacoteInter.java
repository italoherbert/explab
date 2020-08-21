package italo.explab.inter.leitura;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.leitura.result.LeituraClasseInterResult;
import italo.explab.inter.leitura.to.LeituraClasseInterVO;
import italo.explab.msg.CodigoErro;
import italo.explab.recursos.classe.Classe;
import italo.explab.recursos.classe.ClasseLista;
import italo.explab.util.ContadorUtil;

public class LeituraClassesPacoteInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();
        
        ClasseLista gClasseLista = (ClasseLista)aplic.getGrupoRaiz().getBloco().getRecursos().getClasseLista();

        String pacote = null;
        
        int j = 0;
        j += contUtil.contaEsps( codigo, i+j );
        
        int cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.PACOTE );
        if ( cont > 0 ) {    
            j += cont;
            j += contUtil.contaEsps( codigo, i+j );
                                              
            cont = contUtil.contaClasseOuPacoteNomeTam( codigo, i+j );
            if ( cont == 0 ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PACOTE_NOME_ESPERADO );
                return new InterResult( erro );
            }
            
            pacote = codigo.getCodigo().substring( i+j, i+j+cont );
            
            j += cont;
            j += contUtil.contaEspsEPontoEVirgulas( codigo, i+j );            
        }    
        
        boolean fim = false;
        while ( !fim && i+j < i2 ) {
            j += contUtil.contaEspsEPontoEVirgulas( codigo, i+j );            
            
            Inter useInter = manager.getCMDsInter().getInstrucoesCMDsMap().get( "use" );
            InterResult result = useInter.interpreta( no, base, aplic, codigo, i+j, i2 );
            if ( result.getJ() == 0  ) {
                fim = true;
                if ( result.getErro() != null )
                    return result;
            }
            j += result.getJ();
        }

        j += contUtil.contaEsps( codigo, i+j );

        while ( i+j < i2 ) {                      
            LeituraClasseInterVO lclasseIVO = new LeituraClasseInterVO( pacote );
            LeituraClasseInterResult result = (LeituraClasseInterResult)manager.getLeituraClasseInter().interpreta( no, base, aplic, codigo, lclasseIVO, i+j, i2 );                                    
            
            if ( result.getJ() == 0 ) {
                return result;
            } else {
                j += result.getJ();
                Classe classe = result.getClasse();
                gClasseLista.criaOuAltera( classe );                            
            }       
            
            j += contUtil.contaEsps( codigo, i+j );
            
            if ( i+j >= i2 ) {
                return new InterResult( j );
            }
        }
                        
        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i, ErroMSGs.CLASSE_ESPERADA, codigo.getArquivo() );
        return new InterResult( erro );
    }
    
}
