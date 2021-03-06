package th.cu.thesis.fur.api.service.ws.authen;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.auth.AuthChallengeParser;
import org.apache.commons.httpclient.auth.AuthenticationException;
import org.apache.commons.httpclient.auth.InvalidCredentialsException;
import org.apache.commons.httpclient.auth.MalformedChallengeException;
import org.apache.http.Header;
import org.apache.http.auth.NTCredentials;
import org.apache.http.util.CharArrayBuffer;

public class NEW_NTLMScheme extends org.apache.http.impl.auth.NTLMScheme implements org.apache.commons.httpclient.auth.AuthScheme {

    @Override
    public String authenticate(final Credentials credentials, final HttpMethod method) throws AuthenticationException {
        org.apache.commons.httpclient.NTCredentials oldCredentials;
        try {
            oldCredentials = (org.apache.commons.httpclient.NTCredentials) credentials;
        } catch (final ClassCastException e) {
            throw new InvalidCredentialsException(
                    "Credentials cannot be used for NTLM authentication: " 
                    + credentials.getClass().getName());
        }
        final org.apache.http.auth.Credentials adaptedCredentials = new NTCredentials(oldCredentials.getUserName(), oldCredentials.getPassword(), oldCredentials.getHost(), oldCredentials.getDomain());

        try {
            final Header header = super.authenticate(adaptedCredentials, null);
            return header.getValue();
        } catch (final org.apache.http.auth.AuthenticationException e) {
            throw new AuthenticationException("AuthenticationException", e);
        }
    }

    @Override
    public void processChallenge(final String challenge) throws MalformedChallengeException {
        final String s = AuthChallengeParser.extractScheme(challenge);
        if (!s.equalsIgnoreCase(getSchemeName())) {
            throw new MalformedChallengeException("Invalid NTLM challenge: " + challenge);
        }
        int challengeIdx = challenge.indexOf(' ');
        final CharArrayBuffer challengeBuffer;
        if(challengeIdx != -1){
            challengeBuffer = new CharArrayBuffer(challenge.length());
            challengeBuffer.append(challenge);
        } else {
            challengeBuffer = new CharArrayBuffer(0);
            challengeIdx = 0;
        }
        try {
            parseChallenge(challengeBuffer, challengeIdx, challengeBuffer.length());
        } catch (final org.apache.http.auth.MalformedChallengeException e) {
            throw new MalformedChallengeException("MalformedChallengeException", e);
        }
    }

    @Override
    @Deprecated
    public String getID() {
        throw new RuntimeException("deprecated vc.bjn.catalyst.forecast.BackportedNTLMScheme.getID()");
    }


    @Override
    @Deprecated
    public String authenticate(final Credentials credentials, final String method, final String uri) throws AuthenticationException {
        throw new RuntimeException("deprecated vc.bjn.catalyst.forecast.BackportedNTLMScheme.authenticate(Credentials, String, String)");
    }
}