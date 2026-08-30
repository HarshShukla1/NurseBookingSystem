import { Brand } from '../layout/NavigationBar'
import { useState } from 'react'

const dashboards = {
  PATIENT: {
    label: 'Patient dashboard', title: 'Your care, all in one place.', description: 'Find the right support, manage care requests, and keep appointments organised.', primaryAction: 'Find a nurse', stats: [['0', 'Upcoming visits'], ['0', 'Care requests'], ['0', 'Saved nurses']], cards: [['Book home care', 'Tell us what care you need and choose a nurse who fits your schedule.'], ['Your appointments', 'View upcoming visits and revisit the details whenever you need them.'], ['Care preferences', 'Keep your address and care needs ready for faster booking.']],
  },
  NURSE: {
    label: 'Nurse dashboard', title: 'Care for patients with confidence.', description: 'Manage your availability, appointments, and professional profile from one focused workspace.', primaryAction: 'Set availability', stats: [['0', 'Upcoming visits'], ['0', 'New requests'], ['Profile', 'Verification status']], cards: [['Manage availability', 'Set the days and times when you are ready to provide care.'], ['Appointment requests', 'Review new patient requests and keep visits on track.'], ['Complete your profile', 'Add your specialties and service areas to help patients find you.']],
  },
  ADMIN: {
    label: 'Administrator dashboard', title: 'Keep NurseCare running smoothly.', description: 'Review nurse credentials, oversee activity, and maintain a trusted care network.', primaryAction: 'Review nurses', stats: [['0', 'Pending verifications'], ['0', 'Active nurses'], ['0', 'Open requests']], cards: [['Nurse verification', 'Review submitted profiles and approve qualified professionals.'], ['Platform activity', 'Monitor appointments, account activity, and service demand.'], ['Account oversight', 'Support patients and nurses while maintaining a safe care network.']],
  },
}

export function Dashboard({ user, onLogout }) {
  const content = dashboards[user.role] || dashboards.PATIENT
  const [adminView, setAdminView] = useState('NURSE')
  return <div className={`dashboard-shell dashboard-${user.role.toLowerCase()}`}>
    <header className="dashboard-header"><div className="container dashboard-header-inner"><Brand /><div className="dashboard-user"><span>{user.email}</span><button className="button-reset dashboard-logout" type="button" onClick={onLogout}>Log out</button></div></div></header>
    <main className="dashboard-main container">
      <section className="dashboard-welcome"><div><span className="eyebrow">{content.label}</span><h1>{content.title}</h1><p>{content.description}</p></div><button className="button button-primary" type="button">{content.primaryAction}</button></section>
      <section className="dashboard-stats" aria-label="Account summary">{content.stats.map(([value, label]) => <article className="dashboard-stat" key={label}><strong>{value}</strong><span>{label}</span></article>)}</section>
      {user.role === 'ADMIN' ? <AdminAccounts activeView={adminView} onChange={setAdminView} /> : <section className="dashboard-section"><div><span className="eyebrow">Get started</span><h2>Your workspace</h2></div><div className="dashboard-card-grid">{content.cards.map(([title, description]) => <article className="dashboard-card" key={title}><h3>{title}</h3><p>{description}</p><button className="dashboard-card-link" type="button">Open <span aria-hidden="true">→</span></button></article>)}</div></section>}
    </main>
  </div>
}

function AdminAccounts({ activeView, onChange }) {
  const isNurseView = activeView === 'NURSE'
  const accountType = isNurseView ? 'Nurses' : 'Patients'
  const description = isNurseView ? 'Review nurse profiles and verification status separately from patient accounts.' : 'Review patient accounts separately from nurse profiles.'
  return <section className="dashboard-section admin-accounts"><div><span className="eyebrow">Account directory</span><h2>Patients and nurses</h2><p className="admin-accounts-copy">Choose an account type to keep administration focused and organised.</p></div><div className="admin-account-tabs" role="tablist" aria-label="Account type"><button className={isNurseView ? 'active' : ''} type="button" role="tab" aria-selected={isNurseView} onClick={() => onChange('NURSE')}>Nurses</button><button className={!isNurseView ? 'active' : ''} type="button" role="tab" aria-selected={!isNurseView} onClick={() => onChange('PATIENT')}>Patients</button></div><article className="admin-directory" role="tabpanel"><div><span className="admin-directory-label">{accountType}</span><h3>{isNurseView ? 'Nurse accounts' : 'Patient accounts'}</h3><p>{description}</p></div><div className="admin-empty-state"><strong>No {accountType.toLowerCase()} to display</strong><span>Accounts will appear here when the protected admin directory is connected.</span></div></article></section>
}
