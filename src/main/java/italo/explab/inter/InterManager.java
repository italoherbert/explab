package italo.explab.inter;

import italo.explab.inter.estruturas.condicional.CompareCasoInter;
import italo.explab.inter.estruturas.condicional.SeInter;
import italo.explab.inter.estruturas.loop.EnquantoInter;
import italo.explab.inter.estruturas.loop.FacaEnquantoInter;
import italo.explab.inter.estruturas.loop.ParaInter;
import italo.explab.inter.estruturas.tentecapture.TenteCaptureInter;
import italo.explab.inter.exp.NaoBoolExpValorInter;
import italo.explab.inter.exp.NaoStringExpValorInter;
import italo.explab.inter.exp.ValorInter;
import italo.explab.inter.exp.bool.BoolCompInter;
import italo.explab.inter.exp.bool.BoolExpInter;
import italo.explab.inter.exp.bool.BoolNaoExpValorInter;
import italo.explab.inter.exp.bool.BoolValorInter;
import italo.explab.inter.exp.func.ChamadaFuncInter;
import italo.explab.inter.exp.mat.ChamadaMatIndicesInter;
import italo.explab.inter.exp.mat.MatInter;
import italo.explab.inter.exp.mat.RealMatInter;
import italo.explab.inter.exp.mat.VetN1IncN2Inter;
import italo.explab.inter.exp.num.NumExpInter;
import italo.explab.inter.exp.num.RealInter;
import italo.explab.inter.exp.obj.EsteChamadaConstrutorInter;
import italo.explab.inter.exp.obj.NovoObjetoInter;
import italo.explab.inter.exp.obj.SuperChamadaConstrutorInter;
import italo.explab.inter.exp.oo.OOVarOuMatELOuFuncInter;
import italo.explab.inter.exp.string.StringInter;
import italo.explab.inter.exp.string.StringExpInter;
import italo.explab.inter.instrucao.BlocoInter;
import italo.explab.inter.instrucao.InstrucaoInter;
import italo.explab.inter.instrucao.ScriptInter;
import italo.explab.inter.instrucao.CMDsInter;
import italo.explab.inter.leitura.LeituraFuncInter;
import italo.explab.inter.leitura.LeituraClasseInter;
import italo.explab.inter.leitura.LeituraConstrutorInter;
import italo.explab.inter.leitura.LeituraExceptionsInter;
import italo.explab.inter.leitura.LeituraParamsECorpoInter;
import italo.explab.inter.params.ChamadaParamsInter;
import italo.explab.inter.exp.var.VarInter;
import italo.explab.inter.exp.incdec.IncDecInter;
import italo.explab.inter.exp.atrib.AtribVarInter;
import italo.explab.inter.exp.mat.el.MatELAcessoInter;
import italo.explab.inter.exp.var.EsteObjVarInter;
import italo.explab.inter.exp.var.VarTalvezComParentesesInter;
import italo.explab.inter.leitura.LeituraFuncValorInter;
import italo.explab.inter.leitura.LeituraClassesPacoteInter;
import italo.explab.inter.leitura.LeituraGlobalClasseInter;
import italo.explab.inter.leitura.LeituraGlobalFuncInter;

public class InterManager {
    
    private final BlocoInter blocoInter = new BlocoInter();
    private final ScriptInter scriptInter = new ScriptInter();
    
    private final CMDsInter cmdsInter = new CMDsInter();
    
    private final LeituraClassesPacoteInter leituraClassesPacoteInter = new LeituraClassesPacoteInter();
    private final LeituraClasseInter leituraClasseInter = new LeituraClasseInter();
    private final LeituraFuncInter leituraFuncInter = new LeituraFuncInter();
    private final LeituraConstrutorInter leituraConstrutorInter = new LeituraConstrutorInter();
    
    private final LeituraExceptionsInter leituraExceptionsInter = new LeituraExceptionsInter();
    private final LeituraParamsECorpoInter leituraParamsECorpoInter = new LeituraParamsECorpoInter();

    private final LeituraFuncValorInter leituraFuncValorInter = new LeituraFuncValorInter();
    
    private final ChamadaParamsInter chamadaParamsInter = new ChamadaParamsInter();
    
    private final ChamadaFuncInter chamadaFuncInter = new ChamadaFuncInter();    
    private final VarInter varInter = new VarInter();
    private final OOVarOuMatELOuFuncInter ooVarOuMatELOuFuncInter = new OOVarOuMatELOuFuncInter();
    
    private final VarTalvezComParentesesInter varTalvezComParentesesInter = new VarTalvezComParentesesInter();
    private final EsteObjVarInter esteObjVarInter = new EsteObjVarInter();
    
    private final ValorInter valorInter = new ValorInter();
    private final NaoBoolExpValorInter naoBoolExpValorInter = new NaoBoolExpValorInter();
    private final NaoStringExpValorInter naoStringExpValorInter = new NaoStringExpValorInter();
    
    private final BoolValorInter boolValorInter = new BoolValorInter();
    private final BoolCompInter boolCompInter = new BoolCompInter();
    private final BoolNaoExpValorInter boolNaoExpValorInter = new BoolNaoExpValorInter();
    private final BoolExpInter boolExpInter = new BoolExpInter();
    
    private final MatInter matrizInter = new MatInter();
    private final RealMatInter realMatInter = new RealMatInter();
    private final ChamadaMatIndicesInter chamadaMatIndicesInter = new ChamadaMatIndicesInter();
    private final VetN1IncN2Inter vetN1IncN2Inter = new VetN1IncN2Inter();
    private final MatELAcessoInter matELAcessoInter = new MatELAcessoInter();
    
    private final RealInter realInter = new RealInter();
    private final NumExpInter numExpInter = new NumExpInter();
    private final IncDecInter incDecInter = new IncDecInter();
    
    private final StringInter stringInter = new StringInter();
    private final StringExpInter stringExpInter = new StringExpInter();
    
    private final NovoObjetoInter novoObjetoInter = new NovoObjetoInter();
    private final EsteChamadaConstrutorInter esteChamadaConstrutorInter = new EsteChamadaConstrutorInter();
    private final SuperChamadaConstrutorInter superChamadaConstrutorInter = new SuperChamadaConstrutorInter();
    
    private final AtribVarInter atribVarInter = new AtribVarInter();
    
    private final LeituraGlobalFuncInter leituraGlobalFuncInter = new LeituraGlobalFuncInter();
    private final LeituraGlobalClasseInter leituraGlobalClasseInter = new LeituraGlobalClasseInter();
    
    private final SeInter seInter = new SeInter();
    private final CompareCasoInter compareCasoInter = new CompareCasoInter();
    private final ParaInter paraInter = new ParaInter();
    private final EnquantoInter enquantoInter = new EnquantoInter();
    private final FacaEnquantoInter facaEnquantoInter = new FacaEnquantoInter();
    private final TenteCaptureInter tenteCaptureInter = new TenteCaptureInter();

    private final InstrucaoInter instrucaoInter = new InstrucaoInter( this );
    
    public InstrucaoInter getInstrucaoInter() {
        return instrucaoInter;
    }
    
    public BlocoInter getBlocoInter() {
        return blocoInter;
    }
    
    public ScriptInter getScriptInter() {
        return scriptInter;
    }
    
    public CMDsInter getCMDsInter() {
        return cmdsInter;
    }

    public LeituraClasseInter getLeituraClasseInter() {
        return leituraClasseInter;
    }

    public LeituraFuncInter getLeituraFuncInter() {
        return leituraFuncInter;
    }

    public LeituraConstrutorInter getLeituraConstrutorInter() {
        return leituraConstrutorInter;
    }

    public LeituraParamsECorpoInter getLeituraParamsECorpoInter() {
        return leituraParamsECorpoInter;
    }

    public LeituraExceptionsInter getLeituraExceptionsInter() {
        return leituraExceptionsInter;
    }

    public ChamadaParamsInter getChamadaParamsInter() {
        return chamadaParamsInter;
    }

    public ChamadaFuncInter getChamadaFuncInter() {
        return chamadaFuncInter;
    }

    public VarInter getVarInter() {
        return varInter;
    }

    public VarTalvezComParentesesInter getVarTalvezComParentesesInter() {
        return varTalvezComParentesesInter;
    }

    public LeituraFuncValorInter getLeituraFuncValorInter() {
        return leituraFuncValorInter;
    }

    public OOVarOuMatELOuFuncInter getOOVarOuMatELOuFuncInter() {
        return ooVarOuMatELOuFuncInter;
    }

    public EsteObjVarInter getEsteObjVarInter() {
        return esteObjVarInter;
    }        

    public ValorInter getValorInter() {
        return valorInter;
    }

    public NaoBoolExpValorInter getNaoBoolExpValorInter() {
        return naoBoolExpValorInter;
    }

    public NaoStringExpValorInter getNaoStringExpValorInter() {
        return naoStringExpValorInter;
    }

    public BoolValorInter getBoolValorInter() {
        return boolValorInter;
    }

    public BoolCompInter getBoolCompInter() {
        return boolCompInter;
    }

    public BoolNaoExpValorInter getBoolNaoExpValorInter() {
        return boolNaoExpValorInter;
    }

    public BoolExpInter getBoolExpInter() {
        return boolExpInter;
    }       

    public MatInter getMatrizInter() {
        return matrizInter;
    }

    public RealMatInter getRealMatInter() {
        return realMatInter;
    }

    public ChamadaMatIndicesInter getChamadaMatIndicesInter() {
        return chamadaMatIndicesInter;
    }

    public VetN1IncN2Inter getVetN1IncN2Inter() {
        return vetN1IncN2Inter;
    }

    public MatELAcessoInter getMatELAcessoInter() {
        return matELAcessoInter;
    }

    public RealInter getRealInter() {
        return realInter;
    }

    public NumExpInter getNumExpInter() {
        return numExpInter;
    }

    public IncDecInter getIncDecInter() {
        return incDecInter;
    }

    public StringInter getStringInter() {
        return stringInter;
    }

    public StringExpInter getStringExpInter() {
        return stringExpInter;
    }

    public NovoObjetoInter getNovoObjetoInter() {
        return novoObjetoInter;
    }

    public EsteChamadaConstrutorInter getEsteChamadaConstrutorInter() {
        return esteChamadaConstrutorInter;
    }

    public SuperChamadaConstrutorInter getSuperChamadaConstrutorInter() {
        return superChamadaConstrutorInter;
    }

    public LeituraClassesPacoteInter getLeituraClassesPacoteInter() {
        return leituraClassesPacoteInter;
    }

    public AtribVarInter getAtribVarInter() {
        return atribVarInter;
    }

    public LeituraGlobalFuncInter getLeituraGlobalFuncInter() {
        return leituraGlobalFuncInter;
    }

    public LeituraGlobalClasseInter getLeituraGlobalClasseInter() {
        return leituraGlobalClasseInter;
    }

    public SeInter getSeInter() {
        return seInter;
    }

    public CompareCasoInter getCompareCasoInter() {
        return compareCasoInter;
    }

    public ParaInter getParaInter() {
        return paraInter;
    }

    public EnquantoInter getEnquantoInter() {
        return enquantoInter;
    }

    public FacaEnquantoInter getFacaEnquantoInter() {
        return facaEnquantoInter;
    }

    public TenteCaptureInter getTenteCaptureInter() {
        return tenteCaptureInter;
    }
    
}
