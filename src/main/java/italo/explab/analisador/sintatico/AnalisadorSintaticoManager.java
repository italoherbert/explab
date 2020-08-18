package italo.explab.analisador.sintatico;

import italo.explab.analisador.sintatico.estruturas.BlocoCodigoAnalisadorSintatico;
import italo.explab.analisador.sintatico.estruturas.condicional.SeAnalisadorSintatico;
import italo.explab.analisador.sintatico.valor.ValorNaoBooleanExpAnalisadorSintatico;
import italo.explab.analisador.sintatico.valor.ValorNaoStringExpAnalisador;
import italo.explab.analisador.sintatico.valor.ValorAnalisadorSintatico;
import italo.explab.analisador.sintatico.var.VarOuChamadaFuncAnalisadorSintatico;
import italo.explab.analisador.sintatico.string.TalvezSemStrIniStringExpAnalisadorSintatico;
import italo.explab.analisador.sintatico.string.StringExpAnalisadorSintatico;
import italo.explab.analisador.sintatico.string.StringAnalisadorSintatico;
import italo.explab.analisador.sintatico.num.NumericaExpAnalisadorSintatico;
import italo.explab.analisador.sintatico.num.NumRealAnalisadorSintatico;
import italo.explab.analisador.sintatico.classe.NovoObjetoAnalisadorSintatico;
import italo.explab.analisador.sintatico.func.ChamadaFuncAnalisadorSintatico;
import italo.explab.analisador.sintatico.num.IncDecAnalisadorSintatico;
import italo.explab.analisador.sintatico.bool.BooleanExpAnalisadorSintatico;
import italo.explab.analisador.sintatico.classe.LeituraClasseAnalisadorSintatico;
import italo.explab.analisador.sintatico.classe.LeituraConstrutorAnalisadorSintatico;
import italo.explab.analisador.sintatico.estruturas.condicional.CasoAnalisadorSintatico;
import italo.explab.analisador.sintatico.estruturas.condicional.CasoBlocoCodigoAnalisadorSintatico;
import italo.explab.analisador.sintatico.estruturas.loop.EnquantoAnalisadorSintatico;
import italo.explab.analisador.sintatico.estruturas.loop.FacaEnquantoAnalisadorSintatico;
import italo.explab.analisador.sintatico.estruturas.loop.ParaAnalisadorSintatico;
import italo.explab.analisador.sintatico.estruturas.tentecapture.TenteCaptureAnalisadorSintatico;
import italo.explab.analisador.sintatico.construtor.EsteChamadaConstrutorAnalisadorSintatico;
import italo.explab.analisador.sintatico.construtor.SuperChamadaConstrutorAnalisadorSintatico;
import italo.explab.analisador.sintatico.cmd.LanceAnalisadorSintatico;
import italo.explab.analisador.sintatico.comentario.ComentarioAnalisadorSintatico;
import italo.explab.analisador.sintatico.func.LeituraFuncAnalisadorSintatico;
import italo.explab.analisador.sintatico.func.LeituraFuncValorAnalisadorSintatico;
import italo.explab.analisador.sintatico.func.TrateEXAnalisadorSintatico;
import italo.explab.analisador.sintatico.instrucoes.CMDsAnalisadorSintatico;
import italo.explab.analisador.sintatico.instrucoes.InstrucaoAnalisadorSintatico;
import italo.explab.analisador.sintatico.mat.MatELAcessoAnalisadorSintatico;
import italo.explab.analisador.sintatico.mat.MatrizAnalisadorSintatico;
import italo.explab.analisador.sintatico.mat.MatrizIndicesAnalisadorSintatico;
import italo.explab.analisador.sintatico.mat.RealMatrizAnalisadorSintatico;
import italo.explab.analisador.sintatico.mat.VetN1IncN2AnalisadorSintatico;
import italo.explab.analisador.sintatico.num.AntIncDecAnalisadorSintatico;
import italo.explab.analisador.sintatico.num.PosIncDecAnalisadorSintatico;
import italo.explab.analisador.sintatico.var.LeituraNumVarAnalisadorSintatico;
import italo.explab.analisador.sintatico.var.LeituraVarAnalisadorSintatico;
import italo.explab.analisador.sintatico.var.VarTalvezComParentesesAnalisadorSintatico;
import italo.explab.analisador.sintatico.var.VarNovoObjetoAnalisadorSintatico;
import italo.explab.util.ContadorUtil;

public class AnalisadorSintaticoManager {
                
    private final AnalisadorSintatico tenteCaptureAnalisador = new TenteCaptureAnalisadorSintatico( this );
    private final AnalisadorSintatico trateEXAnalisador = new TrateEXAnalisadorSintatico( this );
    private final AnalisadorSintatico lanceAnalisador = new LanceAnalisadorSintatico( this );
    
    private final AnalisadorSintatico numRealAnalisador = new NumRealAnalisadorSintatico();
    private final AnalisadorSintatico varOuChamadaFuncAnalisador = new VarOuChamadaFuncAnalisadorSintatico( this );
    private final AnalisadorSintatico varNovoObjetoAnalisador = new VarNovoObjetoAnalisadorSintatico( this );
    private final AnalisadorSintatico leituraVarAnalisador = new LeituraVarAnalisadorSintatico( this );
    private final AnalisadorSintatico leituraNumVarAnalisador = new LeituraNumVarAnalisadorSintatico( this );
    private final AnalisadorSintatico parametrosAnalisador = new ParametrosAnalisadorSintatico( this );
    private final AnalisadorSintatico chamadaFuncAnalisador = new ChamadaFuncAnalisadorSintatico( this );
    private final AnalisadorSintatico leituraFuncValorAnalisador = new LeituraFuncValorAnalisadorSintatico( this );
    private final AnalisadorSintatico esteChamadaFuncAnalisador = new ChamadaFuncAnalisadorSintatico( this );
    private final AnalisadorSintatico incDecAnalisador = new IncDecAnalisadorSintatico( this );
    private final AnalisadorSintatico antIncDecAnalisador = new AntIncDecAnalisadorSintatico( this );
    private final AnalisadorSintatico posIncDecAnalisador = new PosIncDecAnalisadorSintatico( this );
    private final AnalisadorSintatico vetN1IncN2Analisador = new VetN1IncN2AnalisadorSintatico( this );
    private final AnalisadorSintatico matAnalisador = new MatrizAnalisadorSintatico( this );
    private final AnalisadorSintatico realMatAnalisador = new RealMatrizAnalisadorSintatico( this );
    private final AnalisadorSintatico matIndicesAnalisador = new MatrizIndicesAnalisadorSintatico( this );
    private final AnalisadorSintatico matELAcessoAnalisador = new MatELAcessoAnalisadorSintatico( this );
    private final AnalisadorSintatico valorNaoBooleanExpAnalisador = new ValorNaoBooleanExpAnalisadorSintatico( this );
    private final AnalisadorSintatico valorNaoStringExpAnalisador = new ValorNaoStringExpAnalisador( this );
    private final AnalisadorSintatico valorAnalisador = new ValorAnalisadorSintatico( this );
    private final AnalisadorSintatico numericaExpAnalisador = new NumericaExpAnalisadorSintatico( this );
    private final AnalisadorSintatico boolExpAnalisador = new BooleanExpAnalisadorSintatico( this );
    private final AnalisadorSintatico novoObjetoAnalisador = new NovoObjetoAnalisadorSintatico( this );
    
    private final AnalisadorSintatico stringAnalisador = new StringAnalisadorSintatico();
    private final AnalisadorSintatico stringExpAnalisador = new StringExpAnalisadorSintatico( this );
    private final AnalisadorSintatico talvezSemStrIniStringExpAnalisador = new TalvezSemStrIniStringExpAnalisadorSintatico( this );
    
    private final AnalisadorSintatico blocoCodigoAnalisador = new BlocoCodigoAnalisadorSintatico( this );
    private final AnalisadorSintatico seAnalisador = new SeAnalisadorSintatico( this );
    private final AnalisadorSintatico casoAnalisador = new CasoAnalisadorSintatico( this );
    private final AnalisadorSintatico enquantoAnalisador = new EnquantoAnalisadorSintatico( this );
    private final AnalisadorSintatico facaEnquantoAnalisador = new FacaEnquantoAnalisadorSintatico( this );
    private final AnalisadorSintatico paraAnalisador = new ParaAnalisadorSintatico( this );
    
    private final AnalisadorSintatico leituraFuncAnalisador = new LeituraFuncAnalisadorSintatico( this );
    private final AnalisadorSintatico leituraConstrutorAnalisador = new LeituraConstrutorAnalisadorSintatico( this );
    private final AnalisadorSintatico leituraClasseAnalisador = new LeituraClasseAnalisadorSintatico( this );
    
    private final AnalisadorSintatico cmdsAnalisador = new CMDsAnalisadorSintatico( this );
    private final AnalisadorSintatico instrucaoAnalisador = new InstrucaoAnalisadorSintatico( this );
    
    private final AnalisadorSintatico casoBlocoCodigoAnalisador = new CasoBlocoCodigoAnalisadorSintatico( this );
    
    private final AnalisadorSintatico esteChamadaConstrutorAnalisador = new EsteChamadaConstrutorAnalisadorSintatico( this );
    private final AnalisadorSintatico superChamadaConstrutorAnalisador = new SuperChamadaConstrutorAnalisadorSintatico( this );
    
    private final AnalisadorSintatico comentarioAnalisador = new ComentarioAnalisadorSintatico( this );
    
    private final AnalisadorSintatico varTalvezComParantesesAnalisador = new VarTalvezComParentesesAnalisadorSintatico( this );
        
    private final ContadorUtil contadorUtil;        

    public AnalisadorSintaticoManager( ContadorUtil contadorUtil ) {
        this.contadorUtil = contadorUtil;
    }
    
    public ContadorUtil getContUtil() {
        return contadorUtil;
    }

    public AnalisadorSintatico getCMDsAnalisador() {
        return cmdsAnalisador;
    }

    public AnalisadorSintatico getInstrucaoAnalisador() {
        return instrucaoAnalisador;
    }

    public AnalisadorSintatico getLeituraFuncAnalisador() {
        return leituraFuncAnalisador;
    }

    public AnalisadorSintatico getLeituraConstrutorAnalisador() {
        return leituraConstrutorAnalisador;
    }

    public AnalisadorSintatico getLeituraClasseAnalisador() {
        return leituraClasseAnalisador;
    }
        
    public AnalisadorSintatico getNumRealAnalisador() {
        return numRealAnalisador;
    }

    public AnalisadorSintatico getVarOuChamadaFuncAnalisador() {
        return varOuChamadaFuncAnalisador;
    }

    public AnalisadorSintatico getVarNovoObjetoAnalisador() {
        return varNovoObjetoAnalisador;
    }

    public AnalisadorSintatico getChamadaFuncAnalisador() {
        return chamadaFuncAnalisador;
    }

    public AnalisadorSintatico getEsteChamadaFuncAnalisador() {
        return esteChamadaFuncAnalisador;
    }

    public AnalisadorSintatico getIncDecAnalisador() {
        return incDecAnalisador;
    }

    public AnalisadorSintatico getAntIncDecAnalisador() {
        return antIncDecAnalisador;
    }

    public AnalisadorSintatico getPosIncDecAnalisador() {
        return posIncDecAnalisador;
    }

    public AnalisadorSintatico getVetN1IncN2Analisador() {
        return vetN1IncN2Analisador;
    }

    public AnalisadorSintatico getRealMatAnalisador() {
        return realMatAnalisador;
    }

    public AnalisadorSintatico getValorNaoBooleanExpAnalisador() {
        return valorNaoBooleanExpAnalisador;
    }

    public AnalisadorSintatico getValorNaoStringExpAnalisador() {
        return valorNaoStringExpAnalisador;
    }

    public AnalisadorSintatico getValorAnalisador() {
        return valorAnalisador;
    }

    public AnalisadorSintatico getLeituraFuncValorAnalisador() {
        return leituraFuncValorAnalisador;
    }

    public AnalisadorSintatico getNumericaExpAnalisador() {
        return numericaExpAnalisador;
    }

    public AnalisadorSintatico getMatIndicesAnalisador() {
        return matIndicesAnalisador;
    }

    public AnalisadorSintatico getMatELAcessoAnalisador() {
        return matELAcessoAnalisador;
    }

    public AnalisadorSintatico getLeituraVarAnalisador() {
        return leituraVarAnalisador;
    }

    public AnalisadorSintatico getLeituraNumVarAnalisador() {
        return leituraNumVarAnalisador;
    }

    public AnalisadorSintatico getParametrosAnalisador() {
        return parametrosAnalisador;
    }

    public AnalisadorSintatico getBoolExpAnalisador() {
        return boolExpAnalisador;
    }

    public AnalisadorSintatico getNovoObjetoAnalisador() {
        return novoObjetoAnalisador;
    }

    public AnalisadorSintatico getStringAnalisador() {
        return stringAnalisador;
    }

    public AnalisadorSintatico getStringExpAnalisador() {
        return stringExpAnalisador;
    }

    public AnalisadorSintatico getTalvezSemStrIniStringExpAnalisador() {
        return talvezSemStrIniStringExpAnalisador;
    }

    public AnalisadorSintatico getMatAnalisador() {
        return matAnalisador;
    }

    public AnalisadorSintatico getBlocoCodigoAnalisador() {
        return blocoCodigoAnalisador;
    }

    public AnalisadorSintatico getSeAnalisador() {
        return seAnalisador;
    }

    public AnalisadorSintatico getCasoAnalisador() {
        return casoAnalisador;
    }

    public AnalisadorSintatico getEnquantoAnalisador() {
        return enquantoAnalisador;
    }

    public AnalisadorSintatico getFacaEnquantoAnalisador() {
        return facaEnquantoAnalisador;
    }

    public AnalisadorSintatico getParaAnalisador() {
        return paraAnalisador;
    }

    public AnalisadorSintatico getCasoBlocoCodigoAnalisador() {
        return casoBlocoCodigoAnalisador;
    }

    public AnalisadorSintatico getEsteChamadaConstrutorAnalisador() {
        return esteChamadaConstrutorAnalisador;
    }

    public AnalisadorSintatico getSuperChamadaConstrutorAnalisador() {
        return superChamadaConstrutorAnalisador;
    }

    public AnalisadorSintatico getTenteCaptureAnalisador() {
        return tenteCaptureAnalisador;
    }

    public AnalisadorSintatico getTrateEXAnalisador() {
        return trateEXAnalisador;
    }

    public AnalisadorSintatico getLanceAnalisador() {
        return lanceAnalisador;
    }

    public AnalisadorSintatico getComentarioAnalisador() {
        return comentarioAnalisador;
    }

    public AnalisadorSintatico getVarTalvezComParentesesAnalisador() {
        return varTalvezComParantesesAnalisador;
    }
            
}
