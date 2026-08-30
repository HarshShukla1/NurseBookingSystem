import { Icon } from '../ui/Icon'

export function Brand() { return <a className="brand" href="#top" aria-label="NurseCare home"><span className="brand-mark"><Icon name="cross" size={21} /></span><span>NurseCare</span></a> }

export function NavigationBar({ onLogin, onAdminLogin, onRegister }) { return <header className="navbar" id="top"><div className="container navbar-inner"><Brand /><nav className="nav-links" aria-label="Main navigation"><a href="#services">Services</a><a href="#how-it-works">How it works</a><a href="#for-nurses">For nurses</a></nav><div className="nav-actions"><button className="login-link button-reset" onClick={onLogin}>Log in</button><button className="admin-login-link button-reset" onClick={onAdminLogin}>Admin login</button><button className="button button-primary nav-button" onClick={onRegister}>Get started</button></div></div></header> }
