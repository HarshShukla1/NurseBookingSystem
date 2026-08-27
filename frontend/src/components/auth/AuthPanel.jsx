import { useState } from 'react'

export function AuthPanel({ initialMode = 'login', initialRole = 'PATIENT', onClose }) {
  const [mode, setMode] = useState(initialMode)
  const [role, setRole] = useState(initialRole)
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [message, setMessage] = useState('')
  const [submitting, setSubmitting] = useState(false)

  async function submit(event) {
    event.preventDefault()
    setSubmitting(true)
    setMessage('')
    try {
      const response = await fetch(`/api/auth/${mode === 'login' ? 'login' : 'register'}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(mode === 'login' ? { email, password } : { email, password, role }),
      })
      const body = await response.json().catch(() => ({}))
      if (!response.ok) throw new Error(body.detail || body.message || 'Unable to complete your request.')
      const accountType = body.role === 'NURSE' ? 'nurse' : 'patient'
      setMessage(`Welcome, ${body.email}. Your ${accountType} account is ready.`)
    } catch (error) {
      setMessage(error.message)
    } finally {
      setSubmitting(false)
    }
  }

  return <div className="auth-backdrop" role="presentation" onMouseDown={onClose}>
    <section className="auth-panel" role="dialog" aria-modal="true" aria-labelledby="auth-title" onMouseDown={event => event.stopPropagation()}>
      <button className="auth-close" type="button" aria-label="Close" onClick={onClose}>×</button>
      <p className="eyebrow">NurseCare account</p>
      <h2 id="auth-title">{mode === 'login' ? 'Welcome back' : 'Create your account'}</h2>
      <p className="auth-copy">{mode === 'login' ? 'Log in as a patient or nurse.' : 'Choose the account that fits how you use NurseCare.'}</p>
      <form onSubmit={submit}>
        {mode === 'register' && <label>Account type<select value={role} onChange={event => setRole(event.target.value)}><option value="PATIENT">Patient or family</option><option value="NURSE">Nurse</option></select></label>}
        <label>Email address<input type="email" autoComplete="email" value={email} onChange={event => setEmail(event.target.value)} required /></label>
        <label>Password<input type="password" autoComplete={mode === 'login' ? 'current-password' : 'new-password'} minLength="8" value={password} onChange={event => setPassword(event.target.value)} required /></label>
        <button className="button button-primary auth-submit" disabled={submitting}>{submitting ? 'Please wait…' : mode === 'login' ? 'Log in' : 'Create account'}</button>
      </form>
      {message && <p className="auth-message" role="status">{message}</p>}
      <button className="auth-switch" type="button" onClick={() => { setMode(mode === 'login' ? 'register' : 'login'); setMessage('') }}>{mode === 'login' ? 'New to NurseCare? Create an account' : 'Already have an account? Log in'}</button>
    </section>
  </div>
}
