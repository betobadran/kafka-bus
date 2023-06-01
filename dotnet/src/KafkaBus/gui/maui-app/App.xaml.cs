namespace br.com.badr.gui.maui {
    using br.com.badr.gui.maui.core.secutiry;
    using br.com.badr.gui.maui.gui.secutiry;

    public partial class App : Application {
        private ISecurityService _securityService;

        public App(ISecurityService securityService) {
            _securityService = securityService;

            InitializeComponent();

            //if (_securityService.IsAuthenticated) {
                MainPage = new AppShell();
            //} else {
              //  MainPage = new NavigationPage(new Login());
            //}
        }
    }
}