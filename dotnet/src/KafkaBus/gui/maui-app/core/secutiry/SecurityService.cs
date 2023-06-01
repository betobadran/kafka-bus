namespace br.com.badr.gui.maui.core.secutiry {
    public class SecurityService : ISecurityService {
        private bool _isAuthenticated = false;
        public bool IsAuthenticated => _isAuthenticated;

        public bool TryLogin(string username, string password, string otp) {
            throw new NotImplementedException();
        }
    }
}
