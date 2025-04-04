export type SnackbarMessageType = 'success' | 'warning' | 'info';

interface SnackbarMessageConfig {
  messageKey: string;
  interpolateparams?: { [key: string]: string | number };
}

export type SnackbarMessage = SnackbarMessageConfig | string;

export interface SnackbarData {
  message: SnackbarMessage;
  type: SnackbarMessageType;
}
