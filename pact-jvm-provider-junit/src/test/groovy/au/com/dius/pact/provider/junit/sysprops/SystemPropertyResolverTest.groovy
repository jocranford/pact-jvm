package au.com.dius.pact.provider.junit.sysprops

import org.junit.Before
import org.junit.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.is

class SystemPropertyResolverTest {

  private SystemPropertyResolver resolver

  @Before
  void setup() {
    resolver = new SystemPropertyResolver()
  }

  @Test
  void 'Returns The System Property With The Provided Name'() {
    assertThat(resolver.resolveValue('java.version'), is(equalTo(System.getProperty('java.version'))))
    assertThat(resolver.resolveValue('java.version:1234'), is(equalTo(System.getProperty('java.version'))))
  }

  @Test
  void 'Returns The Environment Variable With The Provided Name If There Is No System Property'() {
    assertThat(resolver.resolveValue('PATH'), is(equalTo(System.getenv('PATH'))))
    assertThat(resolver.resolveValue('PATH:1234'), is(equalTo(System.getenv('PATH'))))
  }

  @Test(expected = RuntimeException)
  void 'Throws An Exception If The Name Is Not Resolved'() {
    resolver.resolveValue('value.that.should.not.be.found!')
  }

  @Test
  void 'Defaults To Any Default Value Provided If The Name Is Not Resolved'() {
    assertThat(resolver.resolveValue('value.that.should.not.be.found!:defaultValue'), is(equalTo('defaultValue')))
  }

  @Test(expected = RuntimeException)
  void 'Throws An Exception If The Name Is Not Resolved And The Default Value Is Empty'() {
    resolver.resolveValue('value.that.should.not.be.found!:')
  }

}
