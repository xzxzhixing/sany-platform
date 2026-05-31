const THEME_KEY = 'sany-theme'
const DARK_CLASS = 'theme-dark'
const LIGHT_CLASS = 'theme-light'

export function getTheme() {
  try {
    const stored = localStorage.getItem(THEME_KEY)
    if (stored === 'dark' || stored === 'light') return stored
  } catch {}
  if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) return 'dark'
  return 'light'
}

export function setTheme(theme) {
  const html = document.documentElement
  html.classList.remove(DARK_CLASS, LIGHT_CLASS)
  html.classList.add(theme === 'dark' ? DARK_CLASS : LIGHT_CLASS)
  html.setAttribute('data-theme', theme)
  try { localStorage.setItem(THEME_KEY, theme) } catch {}
}

export function toggleTheme() {
  const next = getTheme() === 'dark' ? 'light' : 'dark'
  setTheme(next)
  return next
}

export function isDark() {
  return document.documentElement.classList.contains(DARK_CLASS)
}

export function initTheme() {
  setTheme(getTheme())
}
