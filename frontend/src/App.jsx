import { Footer } from './components/layout/Footer'
import { NavigationBar } from './components/layout/NavigationBar'
import { HeroSection } from './components/sections/HeroSection'
import { HowItWorksSection } from './components/sections/HowItWorksSection'
import { ServicesSection } from './components/sections/ServicesSection'
import { JoinUsSection } from './components/sections/JoinUsSection'
import { AuthPanel } from './components/auth/AuthPanel'
import { useState } from 'react'
import './App.css'

function App() {
  const [auth, setAuth] = useState(null)
  const openLogin = () => setAuth({ mode: 'login', role: 'PATIENT' })
  const openRegister = role => setAuth({ mode: 'register', role })
  return <div className="site-shell"><NavigationBar onLogin={openLogin} onRegister={() => openRegister('PATIENT')} /><main><HeroSection /><ServicesSection /><HowItWorksSection /><JoinUsSection onRegister={openRegister} /></main><Footer />{auth && <AuthPanel initialMode={auth.mode} initialRole={auth.role} onClose={() => setAuth(null)} />}</div>
}

export default App
