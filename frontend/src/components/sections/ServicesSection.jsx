import { Icon } from '../ui/Icon'

const services = [
  ['Post-hospital care', 'Experienced support as you recover and settle safely back at home.', 'heart'],
  ['Elderly care', 'Respectful, dependable assistance that helps loved ones live well at home.', 'heart'],
  ['Clinical support', 'Skilled nursing care for everyday health needs and ongoing support.', 'calendar'],
]

export function ServicesSection() { return <section className="services" id="services"><div className="container"><span className="eyebrow">Care tailored to you</span><h2 className="section-heading">Support for life at home</h2><p className="section-copy">Connect with qualified nurses for compassionate, convenient care that fits your needs.</p><div className="service-grid">{services.map(([title, description, icon]) => <article className="service-card" key={title}><div className="service-icon"><Icon name={icon} /></div><h3>{title}</h3><p>{description}</p></article>)}</div></div></section> }
