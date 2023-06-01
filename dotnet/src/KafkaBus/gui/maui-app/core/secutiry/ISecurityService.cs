namespace br.com.badr.gui.maui.core.secutiry {
    public interface ISecurityService {
        bool IsAuthenticated { get; }
        bool TryLogin(String username, String password, String otp);
    }
}
