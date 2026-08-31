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
  const [availabilityOpen, setAvailabilityOpen] = useState(false)
  const [availability, setAvailability] = useState(() => {
    const saved = localStorage.getItem(`nurse-availability:${user.email}`)
    return saved ? JSON.parse(saved) : { days: ['MON', 'TUE', 'WED', 'THU', 'FRI'], startTime: '09:00', endTime: '17:00' }
  })
  const [availabilitySaved, setAvailabilitySaved] = useState(false)
  const [bookingOpen, setBookingOpen] = useState(false)
  const [bookedSlots, setBookedSlots] = useState(() => JSON.parse(localStorage.getItem('patient-booked-slots') || '[]'))
  const openAvailability = () => { setAvailabilitySaved(false); setAvailabilityOpen(true) }
  const toggleDay = day => setAvailability(current => ({ ...current, days: current.days.includes(day) ? current.days.filter(selected => selected !== day) : [...current.days, day] }))
  const saveAvailability = event => { event.preventDefault(); localStorage.setItem(`nurse-availability:${user.email}`, JSON.stringify(availability)); setAvailabilitySaved(true) }
  const reserveSlot = slot => {
    const nextBookings = [...bookedSlots, slot]
    localStorage.setItem('patient-booked-slots', JSON.stringify(nextBookings))
    setBookedSlots(nextBookings)
  }
  return <div className={`dashboard-shell dashboard-${user.role.toLowerCase()}`}>
    <header className="dashboard-header"><div className="container dashboard-header-inner"><Brand /><div className="dashboard-user"><span>{user.email}</span><button className="button-reset dashboard-logout" type="button" onClick={onLogout}>Log out</button></div></div></header>
    <main className="dashboard-main container">
      <section className="dashboard-welcome"><div><span className="eyebrow">{content.label}</span><h1>{content.title}</h1><p>{content.description}</p></div><button className="button button-primary" type="button" onClick={user.role === 'NURSE' ? openAvailability : user.role === 'PATIENT' ? () => setBookingOpen(true) : undefined}>{content.primaryAction}</button></section>
      <section className="dashboard-stats" aria-label="Account summary">{content.stats.map(([value, label]) => <article className="dashboard-stat" key={label}><strong>{value}</strong><span>{label}</span></article>)}</section>
      {user.role === 'NURSE' && availabilityOpen && <AvailabilityEditor availability={availability} onChange={setAvailability} onToggleDay={toggleDay} onClose={() => setAvailabilityOpen(false)} onSave={saveAvailability} saved={availabilitySaved} />}
      {user.role === 'PATIENT' && bookingOpen && <BookingEditor bookedSlots={bookedSlots} onClose={() => setBookingOpen(false)} onReserve={reserveSlot} />}
      {user.role === 'ADMIN' ? <AdminAccounts activeView={adminView} onChange={setAdminView} /> : <section className="dashboard-section"><div><span className="eyebrow">Get started</span><h2>Your workspace</h2></div><div className="dashboard-card-grid">{content.cards.map(([title, description]) => <article className="dashboard-card" key={title}><h3>{title}</h3><p>{description}</p><button className="dashboard-card-link" type="button">Open <span aria-hidden="true">→</span></button></article>)}</div></section>}
    </main>
  </div>
}

function AvailabilityEditor({ availability, onChange, onToggleDay, onClose, onSave, saved }) {
  const days = [['MON', 'Mon'], ['TUE', 'Tue'], ['WED', 'Wed'], ['THU', 'Thu'], ['FRI', 'Fri'], ['SAT', 'Sat'], ['SUN', 'Sun']]
  return <section className="availability-editor dashboard-section" aria-labelledby="availability-title">
    <div className="availability-editor-heading"><div><span className="eyebrow">Your schedule</span><h2 id="availability-title">Set your available time</h2><p>Patients will only be able to request care during the times you choose.</p></div><button className="button-reset availability-close" type="button" onClick={onClose} aria-label="Close availability settings">×</button></div>
    <form onSubmit={onSave}>
      <fieldset className="availability-days"><legend>Available days</legend><div>{days.map(([value, label]) => <button className={availability.days.includes(value) ? 'selected' : ''} type="button" key={value} aria-pressed={availability.days.includes(value)} onClick={() => onToggleDay(value)}>{label}</button>)}</div></fieldset>
      <div className="availability-times"><label>Start time<input type="time" value={availability.startTime} onChange={event => onChange({ ...availability, startTime: event.target.value })} required /></label><label>End time<input type="time" value={availability.endTime} onChange={event => onChange({ ...availability, endTime: event.target.value })} required /></label></div>
      <div className="availability-actions"><button className="button button-primary" type="submit" disabled={!availability.days.length}>Save availability</button>{saved && <p role="status">Availability saved for {availability.days.length} day{availability.days.length === 1 ? '' : 's'} each week.</p>}</div>
    </form>
  </section>
}

function BookingEditor({ bookedSlots, onClose, onReserve }) {
  const [selectedSlot, setSelectedSlot] = useState(null)
  const slots = getAvailableSlots().filter(slot => !bookedSlots.some(booking => booking.id === slot.id))
  const confirmBooking = () => { if (selectedSlot) { onReserve(selectedSlot); setSelectedSlot(null) } }
  return <section className="booking-editor dashboard-section" aria-labelledby="booking-title">
    <div className="availability-editor-heading"><div><span className="eyebrow">Book care</span><h2 id="booking-title">Available nurse slots</h2><p>Choose an available time that works for you. Each appointment is one hour.</p></div><button className="button-reset availability-close" type="button" onClick={onClose} aria-label="Close booking">×</button></div>
    {slots.length ? <><div className="booking-slot-list">{slots.map(slot => <button type="button" className={selectedSlot?.id === slot.id ? 'selected' : ''} key={slot.id} onClick={() => setSelectedSlot(slot)}><strong>{slot.nurse}</strong><span>{slot.dateLabel} · {slot.time}</span></button>)}</div><div className="availability-actions"><button className="button button-primary" type="button" disabled={!selectedSlot} onClick={confirmBooking}>Confirm booking</button>{selectedSlot && <p role="status">Selected: {selectedSlot.nurse}, {selectedSlot.dateLabel} at {selectedSlot.time}.</p>}</div></> : <div className="booking-empty-state"><strong>No available slots yet</strong><span>When a nurse sets their availability, their upcoming appointment times will appear here.</span></div>}
  </section>
}

function getAvailableSlots() {
  const dayCodes = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT']
  const slots = []
  for (let index = 0; index < localStorage.length; index += 1) {
    const key = localStorage.key(index)
    if (!key?.startsWith('nurse-availability:')) continue
    try {
      const availability = JSON.parse(localStorage.getItem(key))
      const nurse = key.replace('nurse-availability:', '')
      for (let offset = 0; offset < 7; offset += 1) {
        const date = new Date()
        date.setHours(0, 0, 0, 0)
        date.setDate(date.getDate() + offset)
        if (!availability.days.includes(dayCodes[date.getDay()])) continue
        const [startHour, startMinute] = availability.startTime.split(':').map(Number)
        const [endHour, endMinute] = availability.endTime.split(':').map(Number)
        for (let hour = startHour; hour < endHour || (hour === endHour && startMinute < endMinute); hour += 1) {
          const start = new Date(date)
          start.setHours(hour, hour === startHour ? startMinute : 0, 0, 0)
          const end = new Date(start)
          end.setHours(end.getHours() + 1)
          if (end.getHours() > endHour || (end.getHours() === endHour && end.getMinutes() > endMinute)) continue
          slots.push({ id: `${nurse}-${start.toISOString()}`, nurse, dateLabel: start.toLocaleDateString(undefined, { weekday: 'short', month: 'short', day: 'numeric' }), time: start.toLocaleTimeString([], { hour: 'numeric', minute: '2-digit' }) })
        }
      }
    } catch { /* Ignore malformed local availability data. */ }
  }
  return slots.sort((a, b) => a.id.localeCompare(b.id))
}

function AdminAccounts({ activeView, onChange }) {
  const isNurseView = activeView === 'NURSE'
  const accountType = isNurseView ? 'Nurses' : 'Patients'
  const description = isNurseView ? 'Review nurse profiles and verification status separately from patient accounts.' : 'Review patient accounts separately from nurse profiles.'
  return <section className="dashboard-section admin-accounts"><div><span className="eyebrow">Account directory</span><h2>Patients and nurses</h2><p className="admin-accounts-copy">Choose an account type to keep administration focused and organised.</p></div><div className="admin-account-tabs" role="tablist" aria-label="Account type"><button className={isNurseView ? 'active' : ''} type="button" role="tab" aria-selected={isNurseView} onClick={() => onChange('NURSE')}>Nurses</button><button className={!isNurseView ? 'active' : ''} type="button" role="tab" aria-selected={!isNurseView} onClick={() => onChange('PATIENT')}>Patients</button></div><article className="admin-directory" role="tabpanel"><div><span className="admin-directory-label">{accountType}</span><h3>{isNurseView ? 'Nurse accounts' : 'Patient accounts'}</h3><p>{description}</p></div><div className="admin-empty-state"><strong>No {accountType.toLowerCase()} to display</strong><span>Accounts will appear here when the protected admin directory is connected.</span></div></article></section>
}
