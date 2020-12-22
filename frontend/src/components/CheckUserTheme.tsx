const checkUsedTheme = (theme: string) => {
    const rootClassList = document.getElementById('root')?.classList;
    const bodyStyle = document.body.style;

    if (theme === 'darktheme') {
        rootClassList!.add('darktheme');
        bodyStyle.backgroundColor = "#707070";
    }
    else {
        rootClassList!.remove('darktheme')
        bodyStyle.backgroundColor = "white";
    }
}

export default checkUsedTheme;