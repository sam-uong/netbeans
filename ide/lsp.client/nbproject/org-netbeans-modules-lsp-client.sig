#Signature file v4.1
#Version 1.2

CLSS public java.lang.Object
cons public init()
meth protected java.lang.Object clone() throws java.lang.CloneNotSupportedException
meth protected void finalize() throws java.lang.Throwable
meth public boolean equals(java.lang.Object)
meth public final java.lang.Class<?> getClass()
meth public final void notify()
meth public final void notifyAll()
meth public final void wait() throws java.lang.InterruptedException
meth public final void wait(long) throws java.lang.InterruptedException
meth public final void wait(long,int) throws java.lang.InterruptedException
meth public int hashCode()
meth public java.lang.String toString()

CLSS public abstract interface org.netbeans.modules.lsp.client.spi.LanguageServerProvider
innr public final static LanguageServerDescription
meth public abstract org.netbeans.modules.lsp.client.spi.LanguageServerProvider$LanguageServerDescription startServer(org.openide.util.Lookup)
 anno 0 org.netbeans.api.annotations.common.CheckForNull()
 anno 1 org.netbeans.api.annotations.common.NonNull()

CLSS public final static org.netbeans.modules.lsp.client.spi.LanguageServerProvider$LanguageServerDescription
 outer org.netbeans.modules.lsp.client.spi.LanguageServerProvider
meth public static org.netbeans.modules.lsp.client.spi.LanguageServerProvider$LanguageServerDescription create(java.io.InputStream,java.io.OutputStream,java.lang.Process)
 anno 0 org.netbeans.api.annotations.common.NonNull()
 anno 1 org.netbeans.api.annotations.common.NonNull()
 anno 2 org.netbeans.api.annotations.common.NonNull()
 anno 3 org.netbeans.api.annotations.common.NullAllowed()
supr java.lang.Object
hfds in,out,process

