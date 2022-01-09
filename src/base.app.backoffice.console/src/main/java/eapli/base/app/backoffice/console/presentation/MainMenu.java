/*
 * Copyright (c) 2013-2019 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.base.app.backoffice.console.presentation;

import eapli.base.app.backoffice.console.presentation.GestaoChaoFabrica.AdicionarDepositoAction;
import eapli.base.app.backoffice.console.presentation.GestaoChaoFabrica.RegistarLinhaProducaoAction;
import eapli.base.app.backoffice.console.presentation.GestaoProducao.*;
import eapli.base.app.common.console.presentation.authz.MyUserMenu;
import eapli.base.Application;
import eapli.base.app.backoffice.console.presentation.GestaoChaoFabrica.AdicionarFicheiroConfigAction;
import eapli.base.app.backoffice.console.presentation.GestaoChaoFabrica.AdicionarMaquinaAction;
import eapli.base.app.backoffice.console.presentation.authz.AddUserUI;
import eapli.base.app.backoffice.console.presentation.authz.ListUsersAction;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.menu.HorizontalMenuRenderer;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

/**
 * TODO split this class in more specialized classes for each menu
 *
 * @author Paulo Gandra Sousa
 */
public class MainMenu extends AbstractUI {

    private static final String RETURN_LABEL = "Voltar ";

    private static final int EXIT_OPTION = 0;

    // USERS
    private static final int ADD_USER_OPTION = 1;
    private static final int LIST_USERS_OPTION = 2;

    //Gestor de chão de fábrica
    private static final int ADICIONAR_MAQUINA = 1;
    private static final int ADICIONAR_LINHA_PRODUCAO = 2;
    private static final int ADICIONAR_DEPOSITO = 3;
    private static final int ADICIONAR_FICHEIRO_CONFIGURACAO = 4;
    
    //Gestor de Produção
    private static final int ESPECIFICAR_FICHA_PRODUCAO = 1;
    private static final int ADICIONAR_MATERIA_PRIMA = 2;
    private static final int DEFINIR_CATEGORIA = 3;
    private static final int ADICIONAR_PRODUTO = 4;
    private static final int CONSULTAR_PRODUTOS_SEM_FICHA_PRODUCAO = 5;
    private static final int IMPORTAR_PRODUTOS_CSV = 6;
    private static final int EXPORTAR_XML = 7;
    private static final int IMPORTAR_ORDENS_CSV = 8;
    private static final int ADICIONAR_ORDEM_PRODUCAO = 9;
    private static final int CONSULTAR_ORDEM_PRODUCAO = 10;
    private static final int CONSULTAR_ORDEM_PRODUCAO_ESTADO = 11;

    // MAIN MENU
    private static final int MY_USER_OPTION = 1;
    private static final int USERS_OPTION = 2;

    private static final String SEPARATOR_LABEL = "--------------";

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

    /**
     * @return true if the user selected the exit option
     */
    @Override
    public boolean doShow() {
        final Menu menu = buildMainMenu();
        final MenuRenderer renderer;
        if (Application.settings().isMenuLayoutHorizontal()) {
            renderer = new HorizontalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        } else {
            renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        }
        return renderer.render();
    }

    @Override
    public String headline() {

        return authz.session().map(s -> "Base [ @" + s.authenticatedUser().identity() + " ]")
                .orElse("Base [ ==Anonymous== ]");
    }

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();

        final Menu myUserMenu = new MyUserMenu();
        mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.ADMIN)) {
            final Menu usersMenu = buildUsersMenu();
            mainMenu.addSubMenu(USERS_OPTION, usersMenu);
        }

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER,
                BaseRoles.GESTOR_CHAO_FABRICA)) {
            final Menu gestorChaoFabricaMenu = buildGestorChaoFabricaMenu();
            mainMenu.addSubMenu(USERS_OPTION, gestorChaoFabricaMenu);
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER,
                BaseRoles.GESTOR_PRODUCAO)) {
            final Menu gestorProducaoMenu = buildGestorProducaoMenu();
            mainMenu.addSubMenu(USERS_OPTION, gestorProducaoMenu);
        }

        mainMenu.addItem(EXIT_OPTION, "Sair", new ExitWithMessageAction("Até uma próxima."));

        return mainMenu;
    }

    private Menu buildUsersMenu() {
        final Menu menu = new Menu("Utilizadores >");

        menu.addItem(ADD_USER_OPTION, "Adicionar um utilizador", new AddUserUI()::show);
        menu.addItem(LIST_USERS_OPTION, "Listar todos os utilizadores", new ListUsersAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildGestorChaoFabricaMenu() {
        final Menu menu = new Menu("Operações >");

        menu.addItem(ADICIONAR_MAQUINA, "Adicionar uma máquina", new AdicionarMaquinaAction());
        menu.addItem(ADICIONAR_LINHA_PRODUCAO, "Adicionar uma linha de produção", new RegistarLinhaProducaoAction());
        menu.addItem(ADICIONAR_DEPOSITO, "Adicionar um depósito", new AdicionarDepositoAction());
        menu.addItem(ADICIONAR_FICHEIRO_CONFIGURACAO,"Adicionar Ficheiro de Configuração", new AdicionarFicheiroConfigAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildGestorProducaoMenu() {
        final Menu menu = new Menu("Operações >");

        menu.addItem(ESPECIFICAR_FICHA_PRODUCAO, "Especificar ficha de produção de um produto", new EspecificarFichaProducaoAction());
        menu.addItem(ADICIONAR_MATERIA_PRIMA, "Adicionar uma matéria-prima", new AdicionarMateriaPrimaAction());
        menu.addItem(DEFINIR_CATEGORIA, "Definir uma categoria", new DefinirCategoriaAction());
        menu.addItem(ADICIONAR_PRODUTO, "Adicionar um produto", new AdicionarProdutoAction());
        menu.addItem(CONSULTAR_PRODUTOS_SEM_FICHA_PRODUCAO, "Consultar produtos sem ficha de produção", new ConsultarProdutosSemFichaProducaoAction());
        menu.addItem(IMPORTAR_PRODUTOS_CSV, "Importar produtos de um CSV", new ImportarProdutosCSVAction());
        menu.addItem(EXPORTAR_XML, "Exportar as instâncias para um XML", new ExportarParaXMLAction());
        menu.addItem(IMPORTAR_ORDENS_CSV,"Importar ordens de produção de um CSV", new ImportarOrdemProducaoCSVAction());
        menu.addItem(ADICIONAR_ORDEM_PRODUCAO, "Adicionar uma ordem de produção manualmente", new AdicionarOrdemProducaoAction());
        menu.addItem(CONSULTAR_ORDEM_PRODUCAO, "Consultar ordens de produção de uma encomenda", new ConsultarOrdemProducaoAction());
        menu.addItem(CONSULTAR_ORDEM_PRODUCAO_ESTADO, "Consultar ordens de produção num determinado estado", new ConsultarOrdensProducaoEstadoAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

}
