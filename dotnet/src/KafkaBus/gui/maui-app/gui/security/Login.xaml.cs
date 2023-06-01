namespace br.com.badr.gui.maui.gui.secutiry;
	using br.com.badr.gui.maui.core.secutiry;

public partial class Login : ContentPage
{
	private ISecurityService _securityService;

	public Login()
	{
		InitializeComponent();
	}

    private void Login_Send(object sender, EventArgs e) {
		var authOk = _securityService.TryLogin("", "", "");
		if (authOk) {

		} else {

		}
    }
}