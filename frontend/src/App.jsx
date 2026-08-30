import { Footer } from './components/layout/Footer'
import { NavigationBar } from './components/layout/NavigationBar'
import { HeroSection } from './components/sections/HeroSection'
import { HowItWorksSection } from './components/sections/HowItWorksSection'
import { ServicesSection } from './components/sections/ServicesSection'
import { JoinUsSection } from './components/sections/JoinUsSection'
import { AuthPanel } from './components/auth/AuthPanel'
import { Dashboard } from './components/dashboard/Dashboard'
import { useState } from 'react'
import './App.css'

function App() {
  const [auth, setAuth] = useState(null)
  const [user, setUser] = useState(null)
  const openLogin = () => setAuth({ mode: 'login', role: 'PATIENT' })
  const openAdminLogin = () => setAuth({ mode: 'login', role: 'ADMIN', requiredRole: 'ADMIN' })
  const openRegister = role => setAuth({ mode: 'register', role })
  if (user) return <Dashboard user={user} onLogout={() => setUser(null)} />
  return <div className="site-shell"><NavigationBar onLogin={openLogin} onAdminLogin={openAdminLogin} onRegister={() => openRegister('PATIENT')} /><main><HeroSection /><ServicesSection /><HowItWorksSection /><JoinUsSection onRegister={openRegister} /></main><Footer />{auth && <AuthPanel initialMode={auth.mode} initialRole={auth.role} requiredRole={auth.requiredRole} onClose={() => setAuth(null)} onLogin={loggedInUser => { setUser(loggedInUser); setAuth(null) }} />}</div>
}

export default App
