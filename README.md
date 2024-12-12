# DecryptKeystorePasswordWithBC
A simple java client to decrypt WSO2 tenant passwords using bouncy castle security provider

# How to decrypt the password

1. Checkout the code and assign the correct values for the following variables.
   - String password = "<base64encoded_password>";
   - String keystorePath = "path_to_the_internal_keystore_of_the_super_tenant";
   - String keystorePassword = "<password_of_the_internal_keystore_of_the_super_tenant>";
   - String keyAlias = "alias_of_the_internal_keystore_of_the_super_tenant";
   - String keyPassword = "private_key_password(usually same as the keystore password)";
2. Run the client
