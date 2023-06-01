namespace br.com.badr.gui.maui {
    using br.com.badr.gui.maui.core.secutiry;
    using br.com.badr.gui.maui.gui.secutiry;
    using Microsoft.Maui.ApplicationModel;

    public partial class MainPage : ContentPage {
        private ISecurityService _securityService;
        int count = 0;

        public MainPage(ISecurityService securityService) {
            _securityService = securityService;
            if (!_securityService.IsAuthenticated) {
                var login = new Login();
                //MainPage = new NavigationPage(login);
            }

            InitializeComponent();
        }

        private void OnCounterClicked(object sender, EventArgs e) {
            count++;

            if (count == 1)
                CounterBtn.Text = $"Clicked {count} time";
            else
                CounterBtn.Text = $"Clicked {count} times";

            SemanticScreenReader.Announce(CounterBtn.Text);
        }
    }
}