const icons = {
  cross: <path d="M12 4v16M4 12h16" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" />,
  arrow: <path d="M5 12h13m-5-5 5 5-5 5" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" />,
  heart: <path d="M12 20s-7-4.5-7-10a4 4 0 0 1 7-2.7A4 4 0 0 1 19 10c0 5.5-7 10-7 10Z" fill="none" stroke="currentColor" strokeWidth="2" strokeLinejoin="round" />,
  calendar: <><rect x="4" y="5.5" width="16" height="14" rx="2" fill="none" stroke="currentColor" strokeWidth="2"/><path d="M8 3.5v4M16 3.5v4M4 10h16" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/></>,
}

export function Icon({ name, size = 24 }) { return <svg width={size} height={size} viewBox="0 0 24 24" aria-hidden="true">{icons[name]}</svg> }
