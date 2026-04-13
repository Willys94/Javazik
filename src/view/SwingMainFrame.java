package view;

import classes.*;
import controller.GuiController;
import controller.PersistenceService;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SwingMainFrame extends JFrame {
    private static final String STYLE_BUTTON = "javazikBtnStyle";
    private static final String BTN_PRIMARY = "primary";
    private static final String BTN_SECONDARY = "secondary";

    private static final Color COLOR_ORANGE = Color.decode("#ff7f00");
    private static final Color COLOR_BRUN = Color.decode("#512c10");
    private static final Color COLOR_TEXTE = Color.decode("#ffead8");
    private static final Color COLOR_FOND = new Color(14, 8, 4);
    private static final Color COLOR_SURFACE = new Color(52, 30, 16);
    private static final Color COLOR_SURFACE_ELEVE = new Color(68, 38, 20);
    private static final Color COLOR_ACCENT = new Color(255, 170, 95);
    private static final Color COLOR_LINE = new Color(255, 127, 0, 90);

    private static final int SIDEBAR_WIDTH = 240;

    private static final Border CARD_OUTLINE = BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 1, 2, 1, COLOR_ORANGE),
            BorderFactory.createEmptyBorder(14, 16, 14, 16)
    );

    private static final Border FIELD_INNER = BorderFactory.createEmptyBorder(8, 10, 8, 10);
    private static final Border FIELD_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 2, COLOR_LINE),
            FIELD_INNER
    );

    private final GuiController controller;
    private final DefaultListModel<String> morceauxModel;
    private final DefaultListModel<String> playlistsModel;
    private final JLabel statutLabel;
    private final JLabel headerUserLabel;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel viewStack;
    private final ButtonGroup navGroup = new ButtonGroup();
    private JToggleButton accueilNavButton;
    private JToggleButton adminNavButton;
    private JTextArea adminStatsArea;
    private JLabel accueilTitreLabel;
    private JProgressBar ecouteProgressBar;
    private JButton pauseLectureBtn;
    private Timer lectureTimer;
    private boolean lectureEnPause;

    private Abonne abonneConnecte;
    private Administrateur adminConnecte;
    private List<Morceau> morceauxAffiches;

    private static final String V_ACCUEIL = "accueil";
    private static final String V_CATALOGUE = "catalogue";
    private static final String V_COMPTE = "compte";
    private static final String V_PLAYLISTS = "playlists";
    private static final String V_ADMIN = "admin";

    public SwingMainFrame(Catalogue catalogue, List<Abonne> abonnes, List<Administrateur> administrateurs,
                          AuthentificationService authService, PersistenceService persistenceService) {
        this.controller = new GuiController(catalogue, abonnes, administrateurs, authService, persistenceService);
        this.morceauxModel = new DefaultListModel<>();
        this.playlistsModel = new DefaultListModel<>();
        this.statutLabel = new JLabel("Pret.");
        this.headerUserLabel = new JLabel(" ");
        this.morceauxAffiches = new ArrayList<>();
        this.viewStack = new JPanel(cardLayout);
        this.viewStack.setOpaque(false);

        setTitle("Javazik");
        setMinimumSize(new Dimension(960, 620));
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                controller.saveAll();
            }
        });

        setContentPane(buildRoot());

        viewStack.add(wrapPage(buildAccueilPanel()), V_ACCUEIL);
        viewStack.add(wrapPage(buildCataloguePanel()), V_CATALOGUE);
        viewStack.add(wrapPage(buildComptePanel()), V_COMPTE);
        viewStack.add(wrapPage(buildPlaylistsPanel()), V_PLAYLISTS);
        viewStack.add(wrapPage(buildAdminPanel()), V_ADMIN);

        cardLayout.show(viewStack, V_ACCUEIL);
        styleDeep(viewStack);

        updateHeaderUser();
        rafraichirListeMorceaux(controller.getMorceaux());
    }

    private JPanel buildRoot() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(COLOR_BRUN);
        root.add(buildHeader(), BorderLayout.NORTH);
        root.add(buildBody(), BorderLayout.CENTER);
        return root;
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_BRUN);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_ORANGE),
                BorderFactory.createEmptyBorder(10, 18, 10, 18)
        ));

        JPanel droite = new JPanel(new GridLayout(2, 1, 0, 2));
        droite.setOpaque(false);
        headerUserLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        headerUserLabel.setForeground(COLOR_TEXTE);
        headerUserLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        droite.add(headerUserLabel);

        JPanel gauche = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        gauche.setOpaque(false);
        JLabel logo = new JLabel("Javazik");
        logo.setForeground(COLOR_TEXTE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        gauche.add(logo);
        header.add(gauche, BorderLayout.WEST);
        header.add(droite, BorderLayout.EAST);
        return header;
    }

    private JPanel buildBody() {
        JPanel body = new GradientBackdrop();
        body.setLayout(new BorderLayout());
        body.add(buildSidebar(), BorderLayout.WEST);
        body.add(viewStack, BorderLayout.CENTER);
        return body;
    }

    private JPanel buildSidebar() {
        JPanel rail = new JPanel();
        rail.setLayout(new BoxLayout(rail, BoxLayout.Y_AXIS));
        rail.setBackground(COLOR_BRUN);
        rail.setPreferredSize(new Dimension(SIDEBAR_WIDTH, 0));
        rail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, COLOR_LINE),
                BorderFactory.createEmptyBorder(18, 12, 18, 12)
        ));

        JLabel navTitle = new JLabel("MENU");
        navTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        navTitle.setForeground(COLOR_ACCENT);
        navTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        navTitle.setBorder(BorderFactory.createEmptyBorder(0, 8, 12, 0));
        rail.add(navTitle);

        accueilNavButton = createNavItem("Accueil", V_ACCUEIL, true);
        rail.add(accueilNavButton);
        rail.add(Box.createVerticalStrut(6));
        rail.add(createNavItem("Catalogue", V_CATALOGUE, false));
        rail.add(Box.createVerticalStrut(6));
        rail.add(createNavItem("Compte", V_COMPTE, false));
        rail.add(Box.createVerticalStrut(6));
        rail.add(createNavItem("Playlists", V_PLAYLISTS, false));
        rail.add(Box.createVerticalStrut(6));
        adminNavButton = createNavItem("Admin", V_ADMIN, false);
        adminNavButton.setVisible(false);
        rail.add(adminNavButton);
        rail.add(Box.createVerticalGlue());

        JButton quitterBtn = createRailActionButton("Quitter");
        JButton deconnexionBtn = createRailActionButton("Deconnexion");
        quitterBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        deconnexionBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        quitterBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        deconnexionBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        deconnexionBtn.addActionListener(e -> {
            stopLectureEnCours("Lecture arretee (deconnexion).");
            abonneConnecte = null;
            adminConnecte = null;
            playlistsModel.clear();
            if (adminNavButton != null) {
                adminNavButton.setVisible(false);
            }
            if (accueilNavButton != null) {
                accueilNavButton.setSelected(true);
            }
            cardLayout.show(viewStack, V_ACCUEIL);
            updateHeaderUser();
            statutLabel.setText("Deconnecte.");
        });
        quitterBtn.addActionListener(e -> {
            int choix = JOptionPane.showConfirmDialog(
                    this,
                    "Voulez-vous vraiment quitter ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (choix == JOptionPane.YES_OPTION) {
                controller.saveAll();
                dispose();
            }
        });
        rail.add(deconnexionBtn);
        rail.add(Box.createVerticalStrut(6));
        rail.add(quitterBtn);

        return rail;
    }

    private JButton createRailActionButton(String label) {
        return new NavRailActionButton(label);
    }

    private JToggleButton createNavItem(String label, String card, boolean selected) {
        JToggleButton b = new NavRailToggle(label);
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        navGroup.add(b);
        b.addActionListener(e -> {
            if (b.isSelected()) {
                cardLayout.show(viewStack, card);
            }
        });
        if (selected) {
            b.setSelected(true);
        }
        return b;
    }

    private JPanel wrapPage(JPanel content) {
        JPanel page = new JPanel(new BorderLayout());
        page.setOpaque(false);
        page.setBorder(BorderFactory.createEmptyBorder(14, 18, 18, 18));
        page.add(content, BorderLayout.CENTER);
        return page;
    }

    private JPanel asCard(JPanel inner) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(COLOR_SURFACE);
        card.setBorder(CARD_OUTLINE);
        card.add(inner, BorderLayout.CENTER);
        return card;
    }

    private TitledBorder sectionTitle(String title) {
        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_LINE),
                title
        );
        tb.setTitleColor(COLOR_ACCENT);
        tb.setTitleFont(new Font("Segoe UI", Font.BOLD, 12));
        return tb;
    }

    private void updateHeaderUser() {
        if (adminConnecte != null) {
            headerUserLabel.setText("Admin : " + adminConnecte.getLogin());
        } else if (abonneConnecte == null) {
            headerUserLabel.setText("Abonne : non connecte");
        } else {
            headerUserLabel.setText("Abonne : " + abonneConnecte.getLogin());
        }
        updateAccueilTitre();
    }

    private void updateAccueilTitre() {
        if (accueilTitreLabel == null) {
            return;
        }
        if (adminConnecte != null) {
            accueilTitreLabel.setText("Bienvenue sur Javazik - " + adminConnecte.getLogin());
        } else if (abonneConnecte != null) {
            accueilTitreLabel.setText("Bienvenue sur Javazik - " + abonneConnecte.getLogin());
        } else {
            accueilTitreLabel.setText("Bienvenue sur Javazik");
        }
    }

    private void stopLectureEnCours(String message) {
        if (lectureTimer != null) {
            lectureTimer.stop();
            lectureTimer = null;
        }
        lectureEnPause = false;
        if (pauseLectureBtn != null) {
            pauseLectureBtn.setText("Pause");
            pauseLectureBtn.setEnabled(false);
        }
        if (ecouteProgressBar != null) {
            ecouteProgressBar.setValue(0);
            ecouteProgressBar.setString("Aucune lecture en cours");
        }
        if (message != null && !message.isEmpty()) {
            statutLabel.setText(message);
        }
    }

    private JPanel buildAccueilPanel() {
        JPanel inner = new JPanel(new BorderLayout(0, 18));
        inner.setOpaque(false);

        JPanel hero = new JPanel(new GridBagLayout());
        hero.setOpaque(false);

        accueilTitreLabel = new JLabel("Bienvenue sur Javazik");
        accueilTitreLabel.setFont(new Font("Segoe UI", Font.BOLD, 54));
        accueilTitreLabel.setForeground(COLOR_TEXTE);
        accueilTitreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        accueilTitreLabel.setVerticalAlignment(SwingConstants.CENTER);

        hero.add(accueilTitreLabel);

        inner.add(hero, BorderLayout.CENTER);
        return asCard(inner);
    }

    private JPanel featureTile(String title, String desc) {
        JPanel wrap = new JPanel(new BorderLayout(0, 0));
        wrap.setOpaque(false);

        JPanel accent = new JPanel();
        accent.setBackground(COLOR_ORANGE);
        accent.setPreferredSize(new Dimension(4, 0));

        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBackground(COLOR_SURFACE_ELEVE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 1, 1, COLOR_LINE),
                BorderFactory.createEmptyBorder(16, 14, 16, 14)
        ));
        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 15));
        t.setForeground(COLOR_TEXTE);
        JLabel d = new JLabel("<html><div style='color:#ffead8;width:200px;line-height:1.35'>" + desc + "</div></html>");
        d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        p.add(t, BorderLayout.NORTH);
        p.add(d, BorderLayout.CENTER);

        wrap.add(accent, BorderLayout.WEST);
        wrap.add(p, BorderLayout.CENTER);
        return wrap;
    }

    private JPanel buildCataloguePanel() {
        JPanel inner = new JPanel(new BorderLayout(12, 12));
        inner.setOpaque(false);

        JPanel headerRow = new JPanel(new BorderLayout());
        headerRow.setOpaque(false);
        JLabel pageTitle = new JLabel("Catalogue");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pageTitle.setForeground(COLOR_TEXTE);
        JLabel pageSub = new JLabel("Parcourir, filtrer et noter les morceaux");
        pageSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pageSub.setForeground(COLOR_ACCENT);
        JPanel titles = new JPanel(new GridLayout(2, 1, 0, 2));
        titles.setOpaque(false);
        titles.add(pageTitle);
        titles.add(pageSub);
        headerRow.add(titles, BorderLayout.WEST);

        JPanel filtres = new JPanel();
        filtres.setLayout(new BoxLayout(filtres, BoxLayout.Y_AXIS));
        filtres.setOpaque(false);
        filtres.setBorder(sectionTitle("Recherche et tri"));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        row1.setOpaque(false);
        JTextField rechercheField = new JTextField(28);
        rechercheField.putClientProperty(STYLE_BUTTON, "field");
        JButton boutonTitre = new JButton("Par titre");
        styleButton(boutonTitre, BTN_PRIMARY);
        JButton boutonStyle = new JButton("Par style");
        styleButton(boutonStyle, BTN_PRIMARY);
        JButton boutonReset = new JButton("Tout afficher");
        styleButton(boutonReset, BTN_SECONDARY);
        JLabel motsClesLabel = new JLabel("Mots-cles");
        motsClesLabel.setForeground(COLOR_TEXTE);
        row1.add(motsClesLabel);
        row1.add(rechercheField);
        row1.add(boutonTitre);
        row1.add(boutonStyle);
        row1.add(boutonReset);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        row2.setOpaque(false);
        JComboBox<String> triCombo = new JComboBox<>(new String[]{"Titre", "Duree", "Ecoutes"});
        JButton boutonTri = new JButton("Appliquer le tri");
        styleButton(boutonTri, BTN_PRIMARY);
        JButton boutonArtiste = new JButton("Par artiste");
        styleButton(boutonArtiste, BTN_SECONDARY);
        JButton boutonGroupe = new JButton("Par groupe");
        styleButton(boutonGroupe, BTN_SECONDARY);
        JButton boutonAlbum = new JButton("Par album");
        styleButton(boutonAlbum, BTN_SECONDARY);
        JLabel triLabel = new JLabel("Tri");
        triLabel.setForeground(COLOR_TEXTE);
        row2.add(triLabel);
        row2.add(triCombo);
        row2.add(boutonTri);
        row2.add(Box.createHorizontalStrut(16));
        JLabel explorerLabel = new JLabel("Explorer");
        explorerLabel.setForeground(COLOR_TEXTE);
        row2.add(explorerLabel);
        row2.add(boutonArtiste);
        row2.add(boutonGroupe);
        row2.add(boutonAlbum);

        filtres.add(row1);
        filtres.add(row2);

        JList<String> morceauxList = new JList<>(morceauxModel);
        morceauxList.setVisibleRowCount(12);
        morceauxList.setFixedCellHeight(54);
        morceauxList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JPanel cell = new JPanel(new BorderLayout());
            cell.setOpaque(true);
            cell.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(1, 1, 2, 1, COLOR_LINE),
                    BorderFactory.createEmptyBorder(12, 14, 12, 14)
            ));
            cell.setBackground(isSelected ? new Color(255, 127, 0, 45) : new Color(36, 20, 11));

            JLabel titleLabel = new JLabel(value);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            titleLabel.setForeground(isSelected ? COLOR_ACCENT : COLOR_TEXTE);

            cell.add(titleLabel, BorderLayout.CENTER);
            return cell;
        });
        JScrollPane scroll = new JScrollPane(morceauxList);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_ORANGE, 1));

        JPanel listeWrap = new JPanel(new BorderLayout());
        listeWrap.setOpaque(false);
        listeWrap.setBorder(sectionTitle("Morceaux"));
        listeWrap.add(scroll, BorderLayout.CENTER);

        JTextArea detailsMorceau = new JTextArea("Clique sur un morceau pour afficher ses caracteristiques.");
        detailsMorceau.setEditable(false);
        detailsMorceau.setLineWrap(true);
        detailsMorceau.setWrapStyleWord(true);
        detailsMorceau.setBackground(new Color(36, 20, 11));
        detailsMorceau.setForeground(COLOR_TEXTE);
        detailsMorceau.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        detailsMorceau.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 2, 1, COLOR_LINE),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        detailsMorceau.setPreferredSize(new Dimension(0, 110));

        JPanel detailsWrap = new JPanel(new BorderLayout());
        detailsWrap.setOpaque(false);
        detailsWrap.setBorder(sectionTitle("Details du morceau"));
        detailsWrap.add(detailsMorceau, BorderLayout.CENTER);
        listeWrap.add(detailsWrap, BorderLayout.NORTH);

        statutLabel.setForeground(COLOR_ACCENT);
        statutLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        ecouteProgressBar = new JProgressBar(0, 100);
        ecouteProgressBar.setValue(0);
        ecouteProgressBar.setStringPainted(true);
        ecouteProgressBar.setString("Aucune lecture en cours");
        ecouteProgressBar.setBackground(new Color(36, 20, 11));
        ecouteProgressBar.setForeground(COLOR_ORANGE);
        ecouteProgressBar.setBorder(BorderFactory.createLineBorder(COLOR_LINE, 1));

        JPanel statusWrap = new JPanel(new BorderLayout(0, 6));
        statusWrap.setOpaque(false);
        statusWrap.add(ecouteProgressBar, BorderLayout.NORTH);
        statusWrap.add(statutLabel, BorderLayout.SOUTH);
        listeWrap.add(statusWrap, BorderLayout.SOUTH);

        JPanel avisCol = new JPanel();
        avisCol.setLayout(new BoxLayout(avisCol, BoxLayout.Y_AXIS));
        avisCol.setOpaque(false);
        avisCol.setBorder(sectionTitle("Avis"));
        JButton noterBtn = new JButton("Noter la selection");
        styleButton(noterBtn, BTN_PRIMARY);
        JButton ecouterBtn = new JButton("Ecouter la selection");
        styleButton(ecouterBtn, BTN_PRIMARY);
        pauseLectureBtn = new JButton("Pause");
        styleButton(pauseLectureBtn, BTN_SECONDARY);
        pauseLectureBtn.setEnabled(false);
        JButton supprimerNoteBtn = new JButton("Supprimer ma note");
        styleButton(supprimerNoteBtn, BTN_SECONDARY);
        JButton voirAvisBtn = new JButton("Voir les avis");
        styleButton(voirAvisBtn, BTN_SECONDARY);
        noterBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        ecouterBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        pauseLectureBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        supprimerNoteBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        voirAvisBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        avisCol.add(ecouterBtn);
        avisCol.add(Box.createVerticalStrut(8));
        avisCol.add(pauseLectureBtn);
        avisCol.add(Box.createVerticalStrut(8));
        avisCol.add(noterBtn);
        avisCol.add(Box.createVerticalStrut(8));
        avisCol.add(supprimerNoteBtn);
        avisCol.add(Box.createVerticalStrut(8));
        avisCol.add(voirAvisBtn);
        avisCol.add(Box.createVerticalGlue());

        JPanel centre = new JPanel(new BorderLayout(14, 0));
        centre.setOpaque(false);
        centre.add(listeWrap, BorderLayout.CENTER);
        avisCol.setPreferredSize(new Dimension(200, 0));
        centre.add(avisCol, BorderLayout.EAST);

        JPanel topBlock = new JPanel(new BorderLayout(0, 10));
        topBlock.setOpaque(false);
        topBlock.add(headerRow, BorderLayout.NORTH);
        topBlock.add(filtres, BorderLayout.SOUTH);

        inner.add(topBlock, BorderLayout.NORTH);
        inner.add(centre, BorderLayout.CENTER);

        boutonTitre.addActionListener(e -> {
            rafraichirListeMorceaux(controller.rechercherMorceauxParTitre(rechercheField.getText()));
            statutLabel.setText("Recherche par titre terminee.");
        });
        boutonStyle.addActionListener(e -> {
            rafraichirListeMorceaux(controller.rechercherMorceauxParStyle(rechercheField.getText()));
            statutLabel.setText("Recherche par style terminee.");
        });
        boutonReset.addActionListener(e -> {
            rafraichirListeMorceaux(controller.getMorceaux());
            statutLabel.setText("Liste complete affichee.");
        });
        boutonTri.addActionListener(e -> {
            int choix = triCombo.getSelectedIndex();
            if (choix == 0) {
                rafraichirListeMorceaux(controller.morceauxTriesParTitre());
            } else if (choix == 1) {
                rafraichirListeMorceaux(controller.morceauxTriesParDuree());
            } else {
                rafraichirListeMorceaux(controller.morceauxTriesParEcoutes());
            }
            statutLabel.setText("Tri applique.");
            detailsMorceau.setText("Clique sur un morceau pour afficher ses caracteristiques.");
        });
        boutonArtiste.addActionListener(e -> {
            String motCle = rechercheField.getText().trim();
            if (motCle.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Saisis un nom d'artiste.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<Artiste> artistes = controller.rechercherArtistesParNom(motCle);
            if (artistes.isEmpty()) {
                statutLabel.setText("Aucun artiste trouve.");
                return;
            }
            Artiste artiste = choisirArtiste(artistes);
            if (artiste == null) {
                return;
            }
            List<Morceau> morceaux = controller.getMorceauxParArtiste(artiste.getNom());
            List<Album> albums = controller.getAlbumsParArtiste(artiste.getNom());
            rafraichirListeMorceaux(morceaux);
            detailsMorceau.setText(formaterParcoursInterprete("Artiste", artiste.getNom(), albums, morceaux));
            statutLabel.setText("Exploration artiste terminee.");
        });
        boutonGroupe.addActionListener(e -> {
            String motCle = rechercheField.getText().trim();
            if (motCle.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Saisis un nom de groupe.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<Groupe> groupes = controller.rechercherGroupesParNom(motCle);
            if (groupes.isEmpty()) {
                statutLabel.setText("Aucun groupe trouve.");
                return;
            }
            Groupe groupe = choisirGroupe(groupes);
            if (groupe == null) {
                return;
            }
            List<Morceau> morceaux = controller.getMorceauxParGroupe(groupe.getNom());
            List<Album> albums = controller.getAlbumsParGroupe(groupe.getNom());
            rafraichirListeMorceaux(morceaux);
            detailsMorceau.setText(formaterParcoursInterprete("Groupe", groupe.getNom(), albums, morceaux));
            statutLabel.setText("Exploration groupe terminee.");
        });
        boutonAlbum.addActionListener(e -> {
            String motCle = rechercheField.getText().trim();
            if (motCle.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Saisis un titre d'album.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<Album> albums = controller.rechercherAlbumsParTitre(motCle);
            if (albums.isEmpty()) {
                statutLabel.setText("Aucun album trouve.");
                return;
            }
            Album album = choisirAlbum(albums);
            if (album == null) {
                return;
            }
            List<Morceau> morceaux = album.getMorceaux();
            rafraichirListeMorceaux(morceaux);
            detailsMorceau.setText(formaterParcoursAlbum(album));
            statutLabel.setText("Exploration album terminee.");
        });

        morceauxList.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            Morceau selection = getMorceauSelectionne(morceauxList);
            if (selection == null) {
                detailsMorceau.setText("Clique sur un morceau pour afficher ses caracteristiques.");
            } else {
                detailsMorceau.setText(formaterDetailsMorceau(selection));
            }
        });

        ecouterBtn.addActionListener(e -> {
            Morceau morceau = getMorceauSelectionne(morceauxList);
            if (morceau == null) {
                JOptionPane.showMessageDialog(this, "Selectionne un morceau.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            stopLectureEnCours(null);

            if (!controller.ecouterMorceau(abonneConnecte, morceau)) {
                JOptionPane.showMessageDialog(this, "Lecture impossible.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int dureeSecondes = Math.max(1, morceau.getDuree());
            int intervalMs = 200;
            int totalTicks = Math.max(1, (dureeSecondes * 1000) / intervalMs);

            ecouteProgressBar.setValue(0);
            ecouteProgressBar.setString("Lecture: 0:00 / " + Morceau.formaterDuree(dureeSecondes));
            statutLabel.setText("Lecture en cours: " + morceau.getTitre());

            final int[] tick = {0};
            lectureTimer = new Timer(intervalMs, evt -> {
                tick[0]++;
                int percent = Math.min(100, (int) ((tick[0] * 100.0) / totalTicks));
                int elapsedSeconds = Math.min(dureeSecondes, (tick[0] * intervalMs) / 1000);
                ecouteProgressBar.setValue(percent);
                ecouteProgressBar.setString("Lecture: " + Morceau.formaterDuree(elapsedSeconds) + " / " + Morceau.formaterDuree(dureeSecondes));

                if (tick[0] >= totalTicks) {
                    ((Timer) evt.getSource()).stop();
                    ecouteProgressBar.setValue(100);
                    ecouteProgressBar.setString("Lecture terminee");
                    statutLabel.setText("Lecture terminee: " + morceau.getTitre());
                    lectureEnPause = false;
                    pauseLectureBtn.setText("Pause");
                    pauseLectureBtn.setEnabled(false);
                    rafraichirListeMorceaux(morceauxAffiches);
                }
            });
            lectureTimer.start();
            pauseLectureBtn.setEnabled(true);
        });

        pauseLectureBtn.addActionListener(e -> {
            if (lectureTimer == null) {
                return;
            }
            if (lectureTimer.isRunning()) {
                lectureTimer.stop();
                lectureEnPause = true;
                pauseLectureBtn.setText("Reprendre");
                statutLabel.setText("Lecture en pause.");
            } else if (lectureEnPause) {
                lectureTimer.start();
                lectureEnPause = false;
                pauseLectureBtn.setText("Pause");
                statutLabel.setText("Lecture reprise.");
            }
        });

        noterBtn.addActionListener(e -> {
            Morceau morceau = getMorceauSelectionne(morceauxList);
            if (morceau == null) {
                JOptionPane.showMessageDialog(this, "Selectionne un morceau.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (abonneConnecte == null) {
                JOptionPane.showMessageDialog(this, "Connecte-toi en tant qu'abonne.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String noteStr = JOptionPane.showInputDialog(this, "Note (1 a 5):");
            if (noteStr == null) {
                return;
            }
            int note;
            try {
                note = Integer.parseInt(noteStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Note invalide.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String commentaire = JOptionPane.showInputDialog(this, "Commentaire (optionnel):");
            if (controller.noterMorceau(abonneConnecte, morceau, note, commentaire)) {
                JOptionPane.showMessageDialog(this, "Avis enregistre.", "Info", JOptionPane.INFORMATION_MESSAGE);
                rafraichirListeMorceaux(morceauxAffiches);
            } else {
                JOptionPane.showMessageDialog(this, "Impossible d'enregistrer l'avis.", "Info", JOptionPane.WARNING_MESSAGE);
            }
        });

        supprimerNoteBtn.addActionListener(e -> {
            Morceau morceau = getMorceauSelectionne(morceauxList);
            if (morceau == null || abonneConnecte == null) {
                JOptionPane.showMessageDialog(this, "Selectionne un morceau et connecte-toi.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (controller.supprimerMaNote(abonneConnecte, morceau)) {
                JOptionPane.showMessageDialog(this, "Note supprimee.", "Info", JOptionPane.INFORMATION_MESSAGE);
                rafraichirListeMorceaux(morceauxAffiches);
            } else {
                JOptionPane.showMessageDialog(this, "Aucune note a supprimer.", "Info", JOptionPane.WARNING_MESSAGE);
            }
        });

        voirAvisBtn.addActionListener(e -> {
            Morceau morceau = getMorceauSelectionne(morceauxList);
            if (morceau == null) {
                JOptionPane.showMessageDialog(this, "Selectionne un morceau.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, controller.getResumeAvis(morceau), "Avis", JOptionPane.INFORMATION_MESSAGE);
        });

        return asCard(inner);
    }

    private JPanel buildComptePanel() {
        JPanel inner = new JPanel(new BorderLayout(0, 16));
        inner.setOpaque(false);

        JLabel pageTitle = new JLabel("Compte");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pageTitle.setForeground(COLOR_TEXTE);
        JLabel pageSub = new JLabel("Connexion abonne ou creation de compte");
        pageSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pageSub.setForeground(COLOR_ACCENT);
        JPanel head = new JPanel(new GridLayout(2, 1, 0, 4));
        head.setOpaque(false);
        head.add(pageTitle);
        head.add(pageSub);

        JPanel grid = new JPanel(new GridLayout(1, 2, 16, 0));
        grid.setOpaque(false);

        JPanel connexion = new JPanel(new GridBagLayout());
        connexion.setOpaque(false);
        connexion.setBorder(sectionTitle("Connexion"));
        JTextField loginField = new JTextField(16);
        JPasswordField pwField = new JPasswordField(16);
        JButton connecterBtn = new JButton("Se connecter");
        styleButton(connecterBtn, BTN_PRIMARY);
        addFormRow(connexion, 0, "Login", loginField);
        addFormRow(connexion, 1, "Mot de passe", pwField);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = new Insets(12, 0, 0, 0);
        connexion.add(connecterBtn, gc);

        JPanel creation = new JPanel(new GridBagLayout());
        creation.setOpaque(false);
        creation.setBorder(sectionTitle("Nouveau compte"));
        JTextField newLoginField = new JTextField(16);
        JPasswordField newPwField = new JPasswordField(16);
        JButton creerBtn = new JButton("Creer le compte");
        styleButton(creerBtn, BTN_PRIMARY);
        addFormRow(creation, 0, "Login", newLoginField);
        addFormRow(creation, 1, "Mot de passe", newPwField);
        gc = new GridBagConstraints();
        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = new Insets(12, 0, 0, 0);
        creation.add(creerBtn, gc);

        connecterBtn.addActionListener(e -> {
            stopLectureEnCours("Lecture arretee (changement de session).");
            String login = loginField.getText();
            String pw = new String(pwField.getPassword());
            Utilisateurs user = controller.connecter(login, pw);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Connexion echouee.", "Info", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Connecte en tant que " + user.getLogin(), "Info", JOptionPane.INFORMATION_MESSAGE);
                if (user instanceof Abonne) {
                    abonneConnecte = (Abonne) user;
                    adminConnecte = null;
                    rafraichirPlaylists();
                    if (adminNavButton != null) {
                        adminNavButton.setVisible(false);
                    }
                } else {
                    abonneConnecte = null;
                    adminConnecte = (Administrateur) user;
                    if (adminNavButton != null) {
                        adminNavButton.setVisible(true);
                        adminNavButton.setSelected(true);
                        cardLayout.show(viewStack, V_ADMIN);
                    }
                    if (adminStatsArea != null) {
                        adminStatsArea.setText(controller.getResumeStatsAdmin());
                    }
                }
                updateHeaderUser();
                if (accueilNavButton != null) {
                    accueilNavButton.setSelected(true);
                }
                cardLayout.show(viewStack, V_ACCUEIL);
                if (adminNavButton != null) {
                    adminNavButton.getParent().revalidate();
                    adminNavButton.getParent().repaint();
                }
            }
        });

        creerBtn.addActionListener(e -> {
            stopLectureEnCours("Lecture arretee (changement de session).");
            String login = newLoginField.getText();
            String pw = new String(newPwField.getPassword());
            Abonne abonne = controller.creerCompte(login, pw);
            if (abonne == null) {
                JOptionPane.showMessageDialog(this, "Creation impossible.", "Info", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Compte cree: " + abonne.getLogin(), "Info", JOptionPane.INFORMATION_MESSAGE);
                abonneConnecte = abonne;
                adminConnecte = null;
                rafraichirPlaylists();
                updateHeaderUser();
                if (accueilNavButton != null) {
                    accueilNavButton.setSelected(true);
                }
                cardLayout.show(viewStack, V_ACCUEIL);
                if (adminNavButton != null) {
                    adminNavButton.setVisible(false);
                }
            }
        });

        grid.add(connexion);
        grid.add(creation);

        inner.add(head, BorderLayout.NORTH);
        inner.add(grid, BorderLayout.CENTER);
        return asCard(inner);
    }

    private void addFormRow(JPanel form, int row, String label, JComponent field) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = row;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = new Insets(6, 0, 6, 12);
        JLabel l = new JLabel(label);
        l.setForeground(COLOR_TEXTE);
        form.add(l, gc);
        gc = new GridBagConstraints();
        gc.gridx = 1;
        gc.gridy = row;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        gc.insets = new Insets(6, 0, 6, 0);
        form.add(field, gc);
    }

    private JPanel buildPlaylistsPanel() {
        JPanel inner = new JPanel(new BorderLayout(0, 14));
        inner.setOpaque(false);

        JLabel pageTitle = new JLabel("Playlists");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pageTitle.setForeground(COLOR_TEXTE);
        JLabel pageSub = new JLabel("Gere tes listes et leur contenu");
        pageSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pageSub.setForeground(COLOR_ACCENT);
        JPanel head = new JPanel(new GridLayout(2, 1, 0, 4));
        head.setOpaque(false);
        head.add(pageTitle);
        head.add(pageSub);

        JList<String> playlistsList = new JList<>(playlistsModel);
        DefaultListModel<String> contenuPlaylistModel = new DefaultListModel<>();
        JList<String> contenuPlaylistList = new JList<>(contenuPlaylistModel);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(playlistsList), new JScrollPane(contenuPlaylistList));
        split.setResizeWeight(0.42);
        split.setBorder(BorderFactory.createLineBorder(COLOR_ORANGE, 1));
        split.setOpaque(false);

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        toolbar.setOpaque(false);
        toolbar.setBorder(sectionTitle("Actions"));

        JButton creerPlaylistBtn = new JButton("Creer");
        JButton ajouterMorceauBtn = new JButton("Ajouter morceau");
        JButton voirContenuBtn = new JButton("Rafraichir contenu");
        JButton retirerMorceauBtn = new JButton("Retirer morceau");
        JButton renommerPlaylistBtn = new JButton("Renommer");
        JButton supprimerPlaylistBtn = new JButton("Supprimer");
        styleButton(creerPlaylistBtn, BTN_PRIMARY);
        styleButton(ajouterMorceauBtn, BTN_PRIMARY);
        styleButton(voirContenuBtn, BTN_SECONDARY);
        styleButton(retirerMorceauBtn, BTN_SECONDARY);
        styleButton(renommerPlaylistBtn, BTN_SECONDARY);
        styleButton(supprimerPlaylistBtn, BTN_SECONDARY);
        toolbar.add(creerPlaylistBtn);
        toolbar.add(ajouterMorceauBtn);
        toolbar.add(voirContenuBtn);
        toolbar.add(retirerMorceauBtn);
        toolbar.add(renommerPlaylistBtn);
        toolbar.add(supprimerPlaylistBtn);

        JPanel centre = new JPanel(new BorderLayout(0, 10));
        centre.setOpaque(false);
        centre.setBorder(sectionTitle("Listes et morceaux"));
        centre.add(split, BorderLayout.CENTER);

        JPanel stack = new JPanel(new BorderLayout(0, 12));
        stack.setOpaque(false);
        stack.add(toolbar, BorderLayout.NORTH);
        stack.add(centre, BorderLayout.CENTER);

        inner.add(head, BorderLayout.NORTH);
        inner.add(stack, BorderLayout.CENTER);

        creerPlaylistBtn.addActionListener(e -> {
            if (abonneConnecte == null) {
                JOptionPane.showMessageDialog(this, "Connecte-toi en tant qu'abonne.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nom = JOptionPane.showInputDialog(this, "Nom de la playlist:");
            if (controller.creerPlaylist(abonneConnecte, nom)) {
                rafraichirPlaylists();
                JOptionPane.showMessageDialog(this, "Playlist creee.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Nom invalide.", "Info", JOptionPane.WARNING_MESSAGE);
            }
        });

        ajouterMorceauBtn.addActionListener(e -> {
            if (abonneConnecte == null) {
                JOptionPane.showMessageDialog(this, "Connecte-toi en tant qu'abonne.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int playlistIndex = playlistsList.getSelectedIndex();
            if (playlistIndex < 0) {
                JOptionPane.showMessageDialog(this, "Selectionne une playlist.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<Playlist> playlists = controller.getPlaylists(abonneConnecte);
            if (playlistIndex >= playlists.size()) {
                return;
            }
            Playlist cible = playlists.get(playlistIndex);
            if (controller.getMorceaux().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun morceau disponible.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<Morceau> tous = controller.getMorceaux();
            String[] options = new String[tous.size()];
            for (int i = 0; i < tous.size(); i++) {
                options[i] = tous.get(i).toString();
            }
            String choix = (String) JOptionPane.showInputDialog(
                    this,
                    "Choisis un morceau:",
                    "Ajout morceau",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            if (choix == null) {
                return;
            }
            int idxChoix = java.util.Arrays.asList(options).indexOf(choix);
            Morceau morceauChoisi = idxChoix >= 0 ? tous.get(idxChoix) : null;
            if (controller.ajouterMorceauAPlaylist(cible, morceauChoisi)) {
                rafraichirPlaylists();
                rafraichirContenuPlaylist(cible, contenuPlaylistModel);
                JOptionPane.showMessageDialog(this, "Morceau ajoute.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        voirContenuBtn.addActionListener(e -> {
            Playlist cible = getPlaylistSelectionnee(playlistsList);
            if (cible == null) {
                JOptionPane.showMessageDialog(this, "Selectionne une playlist.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            rafraichirContenuPlaylist(cible, contenuPlaylistModel);
        });

        retirerMorceauBtn.addActionListener(e -> {
            Playlist cible = getPlaylistSelectionnee(playlistsList);
            if (cible == null) {
                JOptionPane.showMessageDialog(this, "Selectionne une playlist.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<Morceau> contenus = controller.getMorceauxPlaylist(cible);
            if (contenus.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Playlist vide.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String[] options = new String[contenus.size()];
            for (int i = 0; i < contenus.size(); i++) {
                options[i] = contenus.get(i).toString();
            }
            String choix = (String) JOptionPane.showInputDialog(
                    this, "Morceau a retirer:", "Retirer",
                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]
            );
            if (choix == null) {
                return;
            }
            int idxRetrait = java.util.Arrays.asList(options).indexOf(choix);
            Morceau cibleMorceau = idxRetrait >= 0 ? contenus.get(idxRetrait) : null;
            if (controller.retirerMorceauDePlaylist(cible, cibleMorceau)) {
                rafraichirPlaylists();
                rafraichirContenuPlaylist(cible, contenuPlaylistModel);
                JOptionPane.showMessageDialog(this, "Morceau retire.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        renommerPlaylistBtn.addActionListener(e -> {
            Playlist cible = getPlaylistSelectionnee(playlistsList);
            if (cible == null) {
                JOptionPane.showMessageDialog(this, "Selectionne une playlist.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nouveauNom = JOptionPane.showInputDialog(this, "Nouveau nom:");
            if (controller.renommerPlaylist(cible, nouveauNom)) {
                rafraichirPlaylists();
                rafraichirContenuPlaylist(cible, contenuPlaylistModel);
                JOptionPane.showMessageDialog(this, "Playlist renommee.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Nom invalide.", "Info", JOptionPane.WARNING_MESSAGE);
            }
        });

        supprimerPlaylistBtn.addActionListener(e -> {
            Playlist cible = getPlaylistSelectionnee(playlistsList);
            if (cible == null || abonneConnecte == null) {
                JOptionPane.showMessageDialog(this, "Selectionne une playlist.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (controller.supprimerPlaylist(abonneConnecte, cible)) {
                rafraichirPlaylists();
                contenuPlaylistModel.clear();
                JOptionPane.showMessageDialog(this, "Playlist supprimee.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return asCard(inner);
    }

    private JPanel buildAdminPanel() {
        JPanel inner = new JPanel(new BorderLayout(0, 12));
        inner.setOpaque(false);

        JLabel pageTitle = new JLabel("Administration");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pageTitle.setForeground(COLOR_TEXTE);
        JLabel pageSub = new JLabel("Gestion du catalogue, des abonnes et des statistiques");
        pageSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pageSub.setForeground(COLOR_ACCENT);
        JPanel head = new JPanel(new GridLayout(2, 1, 0, 4));
        head.setOpaque(false);
        head.add(pageTitle);
        head.add(pageSub);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        actions.setOpaque(false);
        actions.setBorder(sectionTitle("Actions admin"));

        JButton addMorceauBtn = new JButton("Ajouter morceau");
        JButton delMorceauBtn = new JButton("Supprimer morceau");
        JButton addAlbumBtn = new JButton("Ajouter album");
        JButton delAlbumBtn = new JButton("Supprimer album");
        JButton addArtisteBtn = new JButton("Ajouter artiste");
        JButton delArtisteBtn = new JButton("Supprimer artiste");
        JButton addGroupeBtn = new JButton("Ajouter groupe");
        JButton delGroupeBtn = new JButton("Supprimer groupe");
        JButton suspendBtn = new JButton("Suspendre abonne");
        JButton reactiverBtn = new JButton("Reactiver abonne");
        JButton supprAbonneBtn = new JButton("Supprimer abonne");
        JButton statsBtn = new JButton("Rafraichir stats");

        JButton[] boutons = new JButton[]{
                addMorceauBtn, delMorceauBtn, addAlbumBtn, delAlbumBtn,
                addArtisteBtn, delArtisteBtn, addGroupeBtn, delGroupeBtn,
                suspendBtn, reactiverBtn, supprAbonneBtn, statsBtn
        };
        for (JButton b : boutons) {
            styleButton(b, BTN_SECONDARY);
            actions.add(b);
        }
        styleButton(statsBtn, BTN_PRIMARY);

        adminStatsArea = new JTextArea("Connecte-toi en admin pour voir et utiliser cette section.");
        adminStatsArea.setEditable(false);
        adminStatsArea.setLineWrap(true);
        adminStatsArea.setWrapStyleWord(true);
        adminStatsArea.setBackground(new Color(36, 20, 11));
        adminStatsArea.setForeground(COLOR_TEXTE);
        adminStatsArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        adminStatsArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 2, 1, COLOR_LINE),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        JPanel centre = new JPanel(new BorderLayout(0, 10));
        centre.setOpaque(false);
        centre.setBorder(sectionTitle("Statistiques"));
        centre.add(new JScrollPane(adminStatsArea), BorderLayout.CENTER);

        inner.add(head, BorderLayout.NORTH);
        inner.add(actions, BorderLayout.CENTER);
        inner.add(centre, BorderLayout.SOUTH);

        addMorceauBtn.addActionListener(e -> {
            if (!isAdminConnecte()) return;
            String titre = JOptionPane.showInputDialog(this, "Titre du morceau:");
            if (titre == null) return;
            String dureeStr = JOptionPane.showInputDialog(this, "Duree (secondes):");
            if (dureeStr == null) return;
            String style = JOptionPane.showInputDialog(this, "Style:");
            if (style == null) return;
            String[] types = {"Artiste", "Groupe"};
            String type = (String) JOptionPane.showInputDialog(this, "Type interprete:", "Type",
                    JOptionPane.PLAIN_MESSAGE, null, types, types[0]);
            if (type == null) return;
            String nom = JOptionPane.showInputDialog(this, "Nom interprete:");
            if (nom == null) return;
            try {
                int duree = Integer.parseInt(dureeStr);
                if (controller.adminAjouterMorceau(titre, duree, style, type, nom)) {
                    rafraichirListeMorceaux(controller.getMorceaux());
                    statutLabel.setText("Morceau ajoute.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Donnees invalides.", "Info", JOptionPane.WARNING_MESSAGE);
            }
        });
        delMorceauBtn.addActionListener(e -> {
            if (!isAdminConnecte()) return;
            Morceau m = choisirMorceau(controller.getMorceaux());
            if (m != null && controller.adminSupprimerMorceau(m)) {
                rafraichirListeMorceaux(controller.getMorceaux());
                statutLabel.setText("Morceau supprime.");
            }
        });
        addAlbumBtn.addActionListener(e -> {
            if (!isAdminConnecte()) return;
            String titre = JOptionPane.showInputDialog(this, "Titre de l'album:");
            if (titre == null) return;
            String anneeStr = JOptionPane.showInputDialog(this, "Annee:");
            if (anneeStr == null) return;
            String[] types = {"Artiste", "Groupe"};
            String type = (String) JOptionPane.showInputDialog(this, "Type interprete:", "Type",
                    JOptionPane.PLAIN_MESSAGE, null, types, types[0]);
            if (type == null) return;
            String nom = JOptionPane.showInputDialog(this, "Nom interprete:");
            if (nom == null) return;
            try {
                int annee = Integer.parseInt(anneeStr);
                if (controller.adminAjouterAlbum(titre, annee, type, nom)) {
                    statutLabel.setText("Album ajoute.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Donnees invalides.", "Info", JOptionPane.WARNING_MESSAGE);
            }
        });
        delAlbumBtn.addActionListener(e -> {
            if (!isAdminConnecte()) return;
            Album a = choisirAlbum(controller.getAlbums());
            if (a != null && controller.adminSupprimerAlbum(a)) statutLabel.setText("Album supprime.");
        });
        addArtisteBtn.addActionListener(e -> {
            if (!isAdminConnecte()) return;
            String nom = JOptionPane.showInputDialog(this, "Nom de l'artiste:");
            if (nom == null) return;
            if (controller.adminAjouterArtiste(nom)) statutLabel.setText("Artiste ajoute.");
        });
        delArtisteBtn.addActionListener(e -> {
            if (!isAdminConnecte()) return;
            Artiste a = choisirArtiste(controller.getArtistes());
            if (a != null && controller.adminSupprimerArtiste(a)) statutLabel.setText("Artiste supprime.");
        });
        addGroupeBtn.addActionListener(e -> {
            if (!isAdminConnecte()) return;
            String nom = JOptionPane.showInputDialog(this, "Nom du groupe:");
            if (nom == null) return;
            if (controller.adminAjouterGroupe(nom)) statutLabel.setText("Groupe ajoute.");
        });
        delGroupeBtn.addActionListener(e -> {
            if (!isAdminConnecte()) return;
            Groupe g = choisirGroupe(controller.getGroupes());
            if (g != null && controller.adminSupprimerGroupe(g)) statutLabel.setText("Groupe supprime.");
        });
        suspendBtn.addActionListener(e -> {
            if (!isAdminConnecte()) return;
            Abonne a = choisirAbonne(controller.getAbonnes());
            if (a != null && controller.adminSuspendreAbonne(a)) statutLabel.setText("Abonne suspendu.");
        });
        reactiverBtn.addActionListener(e -> {
            if (!isAdminConnecte()) return;
            Abonne a = choisirAbonne(controller.getAbonnes());
            if (a != null && controller.adminReactiverAbonne(a)) statutLabel.setText("Abonne reactive.");
        });
        supprAbonneBtn.addActionListener(e -> {
            if (!isAdminConnecte()) return;
            Abonne a = choisirAbonne(controller.getAbonnes());
            if (a != null && controller.adminSupprimerAbonne(a)) statutLabel.setText("Abonne supprime.");
        });
        statsBtn.addActionListener(e -> {
            if (!isAdminConnecte()) return;
            adminStatsArea.setText(controller.getResumeStatsAdmin());
        });

        return asCard(inner);
    }

    private void styleButton(JButton b, String kind) {
        b.putClientProperty(STYLE_BUTTON, kind);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    private void styleDeep(Component c) {
        if (c instanceof JComponent && Boolean.TRUE.equals(((JComponent) c).getClientProperty("noRecolor"))) {
            if (c instanceof Container) {
                for (Component child : ((Container) c).getComponents()) {
                    styleDeep(child);
                }
            }
            return;
        }
        if (c instanceof JPanel) {
            JPanel p = (JPanel) c;
            if (!p.isOpaque() && p.getClientProperty("keepTransparent") == null) {
                // leave transparent panels
            } else if (p.isOpaque()) {
                if (p.getBackground() == null || p.getBackground().equals(new Color(238, 238, 238))) {
                    p.setBackground(COLOR_SURFACE);
                }
                p.setForeground(COLOR_TEXTE);
            }
        } else if (c instanceof JLabel) {
            JLabel l = (JLabel) c;
            if (l.getForeground() == null || l.getForeground().equals(Color.BLACK)) {
                l.setForeground(COLOR_TEXTE);
            }
        } else if (c instanceof JButton) {
            JButton b = (JButton) c;
            String kind = (String) b.getClientProperty(STYLE_BUTTON);
            if (BTN_SECONDARY.equals(kind)) {
                b.setBackground(COLOR_SURFACE_ELEVE);
                b.setForeground(COLOR_TEXTE);
                b.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_ORANGE, 1),
                        BorderFactory.createEmptyBorder(8, 14, 8, 14)
                ));
            } else {
                b.setBackground(COLOR_ORANGE);
                b.setForeground(COLOR_FOND);
                b.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(180, 70, 0)),
                        BorderFactory.createEmptyBorder(8, 16, 8, 16)
                ));
            }
        } else if (c instanceof JTextField) {
            JTextField f = (JTextField) c;
            f.setBackground(new Color(40, 22, 12));
            f.setForeground(COLOR_TEXTE);
            f.setCaretColor(COLOR_TEXTE);
            f.setBorder(FIELD_BORDER);
        } else if (c instanceof JPasswordField) {
            JPasswordField f = (JPasswordField) c;
            f.setBackground(new Color(40, 22, 12));
            f.setForeground(COLOR_TEXTE);
            f.setCaretColor(COLOR_TEXTE);
            f.setBorder(FIELD_BORDER);
        } else if (c instanceof JComboBox) {
            JComboBox<?> cb = (JComboBox<?>) c;
            cb.setBackground(new Color(40, 22, 12));
            cb.setForeground(COLOR_TEXTE);
            cb.setBorder(FIELD_BORDER);
        } else if (c instanceof JList) {
            JList<?> list = (JList<?>) c;
            list.setBackground(new Color(36, 20, 11));
            list.setForeground(COLOR_TEXTE);
            list.setSelectionBackground(COLOR_ORANGE);
            list.setSelectionForeground(COLOR_FOND);
        } else if (c instanceof JScrollPane) {
            JScrollPane sp = (JScrollPane) c;
            sp.getViewport().setBackground(new Color(36, 20, 11));
            sp.setBorder(BorderFactory.createLineBorder(COLOR_LINE, 1));
        } else if (c instanceof JSplitPane) {
            ((JSplitPane) c).setDividerSize(6);
        }

        if (c instanceof Container) {
            for (Component child : ((Container) c).getComponents()) {
                styleDeep(child);
            }
        }
    }

    private void rafraichirListeMorceaux(List<Morceau> morceaux) {
        morceauxAffiches = new ArrayList<>(morceaux);
        morceauxModel.clear();
        for (Morceau morceau : morceaux) {
            morceauxModel.addElement(morceau.getTitre());
        }
    }

    private String formaterDetailsMorceau(Morceau morceau) {
        return "Titre: " + morceau.getTitre()
                + "\nInterprete: " + morceau.getInterprete().getNom()
                + "\nDuree: " + Morceau.formaterDuree(morceau.getDuree())
                + "\nStyle: " + morceau.getStyle()
                + "\nEcoutes: " + morceau.getNbEcoutes();
    }

    private String formaterParcoursInterprete(String type, String nom, List<Album> albums, List<Morceau> morceaux) {
        StringBuilder sb = new StringBuilder();
        sb.append(type).append(": ").append(nom).append("\n");
        sb.append("Albums: ").append(albums.size()).append("\n");
        for (Album album : albums) {
            sb.append("- ").append(album.getTitre()).append(" (").append(album.getAnnee()).append(")\n");
        }
        sb.append("Morceaux: ").append(morceaux.size()).append("\n");
        for (Morceau morceau : morceaux) {
            sb.append("- ").append(morceau.getTitre()).append("\n");
        }
        return sb.toString();
    }

    private String formaterParcoursAlbum(Album album) {
        StringBuilder sb = new StringBuilder();
        sb.append("Album: ").append(album.getTitre()).append(" (").append(album.getAnnee()).append(")\n");
        sb.append("Interprete: ").append(album.getInterprete().getNom()).append("\n");
        sb.append("Morceaux: ").append(album.getMorceaux().size()).append("\n");
        for (Morceau morceau : album.getMorceaux()) {
            sb.append("- ").append(morceau.getTitre()).append("\n");
        }
        return sb.toString();
    }

    private Artiste choisirArtiste(List<Artiste> artistes) {
        if (artistes.size() == 1) {
            return artistes.get(0);
        }
        String[] options = new String[artistes.size()];
        for (int i = 0; i < artistes.size(); i++) {
            options[i] = artistes.get(i).getNom();
        }
        String choix = (String) JOptionPane.showInputDialog(
                this,
                "Selectionne un artiste:",
                "Exploration artiste",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choix == null) {
            return null;
        }
        for (Artiste artiste : artistes) {
            if (artiste.getNom().equals(choix)) {
                return artiste;
            }
        }
        return null;
    }

    private Groupe choisirGroupe(List<Groupe> groupes) {
        if (groupes.size() == 1) {
            return groupes.get(0);
        }
        String[] options = new String[groupes.size()];
        for (int i = 0; i < groupes.size(); i++) {
            options[i] = groupes.get(i).getNom();
        }
        String choix = (String) JOptionPane.showInputDialog(
                this,
                "Selectionne un groupe:",
                "Exploration groupe",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choix == null) {
            return null;
        }
        for (Groupe groupe : groupes) {
            if (groupe.getNom().equals(choix)) {
                return groupe;
            }
        }
        return null;
    }

    private Album choisirAlbum(List<Album> albums) {
        if (albums.size() == 1) {
            return albums.get(0);
        }
        String[] options = new String[albums.size()];
        for (int i = 0; i < albums.size(); i++) {
            options[i] = albums.get(i).getTitre() + " (" + albums.get(i).getAnnee() + ")";
        }
        String choix = (String) JOptionPane.showInputDialog(
                this,
                "Selectionne un album:",
                "Exploration album",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choix == null) {
            return null;
        }
        for (int i = 0; i < albums.size(); i++) {
            String option = albums.get(i).getTitre() + " (" + albums.get(i).getAnnee() + ")";
            if (option.equals(choix)) {
                return albums.get(i);
            }
        }
        return null;
    }

    private Morceau choisirMorceau(List<Morceau> morceaux) {
        if (morceaux == null || morceaux.isEmpty()) {
            return null;
        }
        String[] options = new String[morceaux.size()];
        for (int i = 0; i < morceaux.size(); i++) {
            options[i] = morceaux.get(i).getTitre();
        }
        String choix = (String) JOptionPane.showInputDialog(
                this,
                "Selectionne un morceau:",
                "Selection morceau",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choix == null) {
            return null;
        }
        for (Morceau m : morceaux) {
            if (m.getTitre().equals(choix)) {
                return m;
            }
        }
        return null;
    }

    private Abonne choisirAbonne(List<Abonne> abonnes) {
        if (abonnes == null || abonnes.isEmpty()) {
            return null;
        }
        String[] options = new String[abonnes.size()];
        for (int i = 0; i < abonnes.size(); i++) {
            options[i] = abonnes.get(i).getLogin();
        }
        String choix = (String) JOptionPane.showInputDialog(
                this,
                "Selectionne un abonne:",
                "Selection abonne",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choix == null) {
            return null;
        }
        for (Abonne a : abonnes) {
            if (a.getLogin().equals(choix)) {
                return a;
            }
        }
        return null;
    }

    private boolean isAdminConnecte() {
        if (adminConnecte == null) {
            JOptionPane.showMessageDialog(this, "Connecte-toi en tant qu'administrateur.", "Info", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private Morceau getMorceauSelectionne(JList<String> morceauxList) {
        int index = morceauxList.getSelectedIndex();
        if (index < 0 || index >= morceauxAffiches.size()) {
            return null;
        }
        return morceauxAffiches.get(index);
    }

    private void rafraichirPlaylists() {
        playlistsModel.clear();
        if (abonneConnecte == null) {
            return;
        }
        for (Playlist p : controller.getPlaylists(abonneConnecte)) {
            playlistsModel.addElement(p.toString());
        }
    }

    private Playlist getPlaylistSelectionnee(JList<String> playlistsList) {
        if (abonneConnecte == null) {
            return null;
        }
        int idx = playlistsList.getSelectedIndex();
        List<Playlist> playlists = controller.getPlaylists(abonneConnecte);
        if (idx < 0 || idx >= playlists.size()) {
            return null;
        }
        return playlists.get(idx);
    }

    private void rafraichirContenuPlaylist(Playlist playlist, DefaultListModel<String> contenuPlaylistModel) {
        contenuPlaylistModel.clear();
        if (playlist == null) {
            return;
        }
        for (Morceau morceau : controller.getMorceauxPlaylist(playlist)) {
            contenuPlaylistModel.addElement(morceau.toString());
        }
    }

    private static final class NavRailToggle extends JToggleButton {
        NavRailToggle(String text) {
            super(text);
            setFont(new Font("Segoe UI", Font.PLAIN, 15));
            setForeground(COLOR_TEXTE);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setMargin(new Insets(10, 14, 10, 14));
            setHorizontalAlignment(SwingConstants.LEFT);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            if (isSelected()) {
                g2.setColor(new Color(255, 127, 0, 45));
                g2.fillRect(0, 0, w, h);
                g2.setColor(COLOR_ORANGE);
                g2.fillRect(0, 0, 4, h);
            } else if (getModel().isRollover()) {
                g2.setColor(new Color(255, 255, 255, 18));
                g2.fillRect(0, 0, w, h);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static final class NavRailActionButton extends JButton {
        NavRailActionButton(String text) {
            super(text);
            setFont(new Font("Segoe UI", Font.PLAIN, 15));
            setForeground(COLOR_TEXTE);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setMargin(new Insets(10, 14, 10, 14));
            setHorizontalAlignment(SwingConstants.LEFT);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            if (getModel().isRollover()) {
                g2.setColor(new Color(255, 255, 255, 18));
                g2.fillRect(0, 0, w, h);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static final class GradientBackdrop extends JPanel {
        GradientBackdrop() {
            setOpaque(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            int w = getWidth();
            int h = getHeight();
            Paint p = new GradientPaint(0, 0, COLOR_FOND, w, h, COLOR_BRUN);
            g2.setPaint(p);
            g2.fillRect(0, 0, w, h);
            g2.setPaint(new RadialGradientPaint(
                    w * 0.2f, 0, Math.max(w, h) * 0.9f,
                    new float[]{0f, 1f},
                    new Color[]{new Color(255, 127, 0, 28), new Color(255, 127, 0, 0)}
            ));
            g2.fillRect(0, 0, w, h);
            g2.setPaint(new RadialGradientPaint(
                    w * 0.85f, h * 0.35f, Math.min(w, h) * 0.55f,
                    new float[]{0f, 1f},
                    new Color[]{new Color(255, 200, 100, 26), new Color(255, 200, 100, 0)}
            ));
            g2.fillRect(0, 0, w, h);
            g2.dispose();
        }
    }

}
