package italo.explab_ide.controller;

import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.gui.principal.projetos.popupmenu.ProjetosPopupMenuListener;

public class ProjetoPopupMenuController implements ProjetosPopupMenuListener {

    private final ExpLabIDEAplic aplic;
    
    public ProjetoPopupMenuController( ExpLabIDEAplic aplic ) {
        this.aplic = aplic;
    }

    @Override
    public void novoArqExpLabAcionado() {
        aplic.getProjetoOuArquivoControllerCtrl().novoArqELAcionado();
    }

    @Override
    public void novoArqVasioAcionado() {
        aplic.getProjetoOuArquivoControllerCtrl().novoArquivoVasioAcionado();
    }

    @Override
    public void novaPastaAcionado() {
        aplic.getProjetoOuArquivoControllerCtrl().novaPastaAcionado();
    }
    
    @Override
    public void executarAcionado() {       
        aplic.getProjetoOuArquivoControllerCtrl().executarScriptAcionado();
    }

    @Override
    public void recarregarAcionado() {
        aplic.getProjetoOuArquivoControllerCtrl().recarregarAcionado();
    }

    @Override
    public void renomearAcionado() {
        aplic.getProjetoOuArquivoControllerCtrl().renomearAcionado();
    }

    @Override
    public void deletarAcionado() {
        aplic.getProjetoOuArquivoControllerCtrl().deletarAcionado();
    }
    
}
