export type OSType = 'win' | 'mac';

export function getOS(): OSType {
  const userAgent = window.navigator.userAgent.toLowerCase();
  return userAgent.indexOf('mac') > -1 ? 'mac' : 'win';
}
