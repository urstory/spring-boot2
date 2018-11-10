package examples.boot.simpleboard.security;

import examples.boot.simpleboard.domain.PersistentLogins;
import examples.boot.simpleboard.repository.PersistentLoginsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class SimpleBoardTokenRepositoryImpl implements PersistentTokenRepository {
    @Autowired
    PersistentLoginsRepository persistentLoginsRepository;

    @Override
    @Transactional
    public void createNewToken(PersistentRememberMeToken token) {
        PersistentLogins persistentLogins = new PersistentLogins();
        persistentLogins.setUsername(token.getUsername());
        persistentLogins.setSeries(token.getSeries());
        persistentLogins.setToken(token.getTokenValue());
        persistentLogins.setLastUsed(token.getDate());
        persistentLoginsRepository.save(persistentLogins);
    }
    /*
    	getJdbcTemplate().update(insertTokenSql, token.getUsername(), token.getSeries(),
				token.getTokenValue(), token.getDate());
     */

    @Override
    @Transactional
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentLogins persistentLogins = persistentLoginsRepository.getOne(series);
        persistentLogins.setToken(tokenValue);
        persistentLogins.setLastUsed(lastUsed);
        persistentLoginsRepository.save(persistentLogins);
    }
    /*
    getJdbcTemplate().update(updateTokenSql, tokenValue, lastUsed, series);
     */

    @Override
    @Transactional(readOnly = true)
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        try {
            PersistentLogins persistentLogins = persistentLoginsRepository.getOne(seriesId);
            if(persistentLogins == null)
                return null;
            PersistentRememberMeToken persistentRememberMeToken = new PersistentRememberMeToken(persistentLogins.getUsername(), persistentLogins.getSeries(), persistentLogins.getToken(), persistentLogins.getLastUsed());
            return persistentRememberMeToken;
        }catch (Exception e) {
        }
        return null;
    }

    @Override
    @Transactional
    public void removeUserTokens(String username) {
        persistentLoginsRepository.deleteByUsername(username);
    }
}
