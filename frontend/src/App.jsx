import { Footer } from './components/layout/Footer'
import { NavigationBar } from './components/layout/NavigationBar'
import { HeroSection } from './components/sections/HeroSection'
import { HowItWorksSection } from './components/sections/HowItWorksSection'
import { ServicesSection } from './components/sections/ServicesSection'
import { JoinUsSection } from './components/sections/JoinUsSection'
import './App.css'

function App() {
  return <div className="site-shell"><NavigationBar /><main><HeroSection /><ServicesSection /><HowItWorksSection /><JoinUsSection /></main><Footer /></div>
}

export default App
